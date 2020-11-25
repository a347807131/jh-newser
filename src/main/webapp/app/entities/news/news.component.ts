import {mixins} from 'vue-class-component';

import {Component, Vue, Inject} from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import {INews} from '@/shared/model/news.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import JhiDataUtils from '@/shared/data/data-utils.service';

import NewsService from './news.service';
import UserExtDetails from "@/entities/user-ext/user-ext-details.component";
import UserExtService from "@/entities/user-ext/user-ext.service";
import LoginService from "@/account/login.service";

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class News extends mixins(JhiDataUtils, AlertMixin) {
  @Inject('newsService') private newsService: () => NewsService;
  @Inject("loginService")
  public loginService: () => LoginService;
  private allCurrentUsersNews = [];

  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = true;
  public totalItems = 0;
  public infiniteId = +new Date();
  public links = {};

  public news: INews[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllNewss();
    this.resetAllCurrentUsersNews();
  }

  public clear(): void {
    this.page = 1;
    this.links = {};
    this.infiniteId += 1;
    this.news = [];
    this.retrieveAllNewss();
  }

  public reset(): void {
    this.page = 1;
    this.infiniteId += 1;
    this.news = [];
    this.retrieveAllNewss();
  }

  public retrieveAllNewss(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.newsService()
      .retrieve(paginationQuery)
      .then(
        res => {
          if (res.data && res.data.length > 0) {
            for (let i = 0; i < res.data.length; i++) {
              this.news.push(res.data[i]);
            }
            if (res.headers && res.headers['link']) {
              this.links = this.parseLinks(res.headers['link']);
            }
          }
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
          if (<any>this.$refs.infiniteLoading) {
            (<any>this.$refs.infiniteLoading).stateChanger.loaded();
            if (this.links !== {} && this.page > this.links['last']) {
              (<any>this.$refs.infiniteLoading).stateChanger.complete();
            }
          }
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: INews): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public preferNews(instance: INews): void {
    this.resetAllCurrentUsersNews();
    var newsId = instance.id
    let elStar = document.getElementById('star-' + newsId);
    if (elStar.getAttribute('class') === 'el-icon-star-off') {
      this.newsService()
        .prefer(newsId)
        .then(() => {
          const message = this.$t('jhdApp.news.preferd', {param: this.removeId});
          this.alertService().showAlert(message, 'danger');
          elStar.setAttribute('class', 'el-icon-star-on')
          // this.stared=true
        });
    } else {
      this.newsService()
        .unprefer(newsId)
        .then(() => {
          const message = this.$t('jhdApp.news.preferd', {param: this.removeId});
          this.alertService().showAlert(message, 'danger');
          elStar.setAttribute('class', 'el-icon-star-off')
          // this.stared=false
        });
    }
  }

  public removeNews(): void {
    this.newsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('jhdApp.news.deleted', {param: this.removeId});
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.reset();
        this.closeDialog();
      });
  }

  public loadMore($state): void {
    if (!this.isFetching) {
      this.page++;
      this.transition();
    }
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllNewss();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.reset();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  resetAllCurrentUsersNews(): void {
    if (!this.$store.getters.authenticated) return;
    const login = this.$store.getters.account ? this.$store.getters.account.login : '';
    this.newsService()
      .findPreferedNewsByLogin(login)
      .then(
        data => {
          if (data && data.length > 0) {
            for (let i = 0; i < data.length; i++) {
              this.allCurrentUsersNews.push(data[i]['id']);
            }
          }
        },
      );
  }

  public openLogin(): void {
    this.$root.$emit('bv::show::modal', 'login-page');
  }
}

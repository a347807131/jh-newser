import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';
import format from 'date-fns/format';
import parse from 'date-fns/parse';
import parseISO from 'date-fns/parseISO';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import UserExtService from '../user-ext/user-ext.service';
import { IUserExt } from '@/shared/model/user-ext.model';

import AlertService from '@/shared/alert/alert.service';
import { INews, News } from '@/shared/model/news.model';
import NewsService from './news.service';

const validations: any = {
  news: {
    title: {},
    source: {},
    link: {
      required,
    },
    kind: {},
    time: {},
    content: {},
  },
};

@Component({
  validations,
})
export default class NewsUpdate extends mixins(JhiDataUtils) {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('newsService') private newsService: () => NewsService;
  public news: INews = new News();

  @Inject('userExtService') private userExtService: () => UserExtService;

  public userExts: IUserExt[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.newsId) {
        vm.retrieveNews(to.params.newsId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.news.id) {
      this.newsService()
        .update(this.news)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhdApp.news.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.newsService()
        .create(this.news)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhdApp.news.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public convertDateTimeFromServer(date: Date): string {
    if (date) {
      return format(date, DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.news[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.news[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.news[field] = parse(event.target.value, DATE_TIME_LONG_FORMAT, new Date());
    } else {
      this.news[field] = null;
    }
  }

  public retrieveNews(newsId): void {
    this.newsService()
      .find(newsId)
      .then(res => {
        res.time = new Date(res.time);
        this.news = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userExtService()
      .retrieve()
      .then(res => {
        this.userExts = res.data;
      });
  }
}

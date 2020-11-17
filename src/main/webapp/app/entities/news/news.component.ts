import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { INews } from '@/shared/model/news.model';
import AlertMixin from '@/shared/alert/alert.mixin';

import NewsService from './news.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class News extends mixins(AlertMixin) {
  @Inject('newsService') private newsService: () => NewsService;
  private removeId: number = null;

  public news: INews[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllNewss();
  }

  public clear(): void {
    this.retrieveAllNewss();
  }

  public retrieveAllNewss(): void {
    this.isFetching = true;

    this.newsService()
      .retrieve()
      .then(
        res => {
          this.news = res.data;
          this.isFetching = false;
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

  public removeNews(): void {
    this.newsService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('jhdApp.news.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();
        this.removeId = null;
        this.retrieveAllNewss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}

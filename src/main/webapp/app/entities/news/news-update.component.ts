import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { INews, News } from '@/shared/model/news.model';
import NewsService from './news.service';

const validations: any = {
  news: {
    title: {},
    source: {},
    link: {},
    kind: {},
    time: {},
    content: {},
  },
};

@Component({
  validations,
})
export default class NewsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('newsService') private newsService: () => NewsService;
  public news: INews = new News();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.newsId) {
        vm.retrieveNews(to.params.newsId);
      }
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

  public retrieveNews(newsId): void {
    this.newsService()
      .find(newsId)
      .then(res => {
        this.news = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}

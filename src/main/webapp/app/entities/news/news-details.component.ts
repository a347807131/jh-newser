import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { INews } from '@/shared/model/news.model';
import NewsService from './news.service';

@Component
export default class NewsDetails extends mixins(JhiDataUtils) {
  @Inject('newsService') private newsService: () => NewsService;
  public news: INews = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.newsId) {
        vm.retrieveNews(to.params.newsId);
      }
    });
  }

  public retrieveNews(newsId) {
    this.newsService()
      .find(newsId)
      .then(res => {
        this.news = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

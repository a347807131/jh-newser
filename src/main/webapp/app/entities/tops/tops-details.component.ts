import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITops } from '@/shared/model/tops.model';
import TopsService from './tops.service';

@Component
export default class TopsDetails extends Vue {
  @Inject('topsService') private topsService: () => TopsService;
  public tops: ITops = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.topsId) {
        vm.retrieveTops(to.params.topsId);
      }
    });
  }

  public retrieveTops(topsId) {
    this.topsService()
      .find(topsId)
      .then(res => {
        this.tops = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

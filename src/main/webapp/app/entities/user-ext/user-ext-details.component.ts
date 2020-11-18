import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUserExt } from '@/shared/model/user-ext.model';
import UserExtService from './user-ext.service';

@Component
export default class UserExtDetails extends Vue {
  @Inject('userExtService') private userExtService: () => UserExtService;
  public userExt: IUserExt = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userExtId) {
        vm.retrieveUserExt(to.params.userExtId);
      }
    });
  }

  public retrieveUserExt(userExtId) {
    this.userExtService()
      .find(userExtId)
      .then(res => {
        this.userExt = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}

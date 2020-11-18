import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import AlertService from '@/shared/alert/alert.service';
import { IUserExt, UserExt } from '@/shared/model/user-ext.model';
import UserExtService from './user-ext.service';

const validations: any = {
  userExt: {
    pthone: {},
  },
};

@Component({
  validations,
})
export default class UserExtUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('userExtService') private userExtService: () => UserExtService;
  public userExt: IUserExt = new UserExt();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userExtId) {
        vm.retrieveUserExt(to.params.userExtId);
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
    if (this.userExt.id) {
      this.userExtService()
        .update(this.userExt)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhdApp.userExt.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.userExtService()
        .create(this.userExt)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhdApp.userExt.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveUserExt(userExtId): void {
    this.userExtService()
      .find(userExtId)
      .then(res => {
        this.userExt = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}

import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ITops, Tops } from '@/shared/model/tops.model';
import TopsService from './tops.service';

const validations: any = {
  tops: {
    title: {},
    link: {},
    source: {},
    time: {},
    seq: {},
    note: {},
  },
};

@Component({
  validations,
})
export default class TopsUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('topsService') private topsService: () => TopsService;
  public tops: ITops = new Tops();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.topsId) {
        vm.retrieveTops(to.params.topsId);
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
    if (this.tops.id) {
      this.topsService()
        .update(this.tops)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhdApp.tops.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.topsService()
        .create(this.tops)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('jhdApp.tops.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTops(topsId): void {
    this.topsService()
      .find(topsId)
      .then(res => {
        this.tops = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UserExtComponent from '@/entities/user-ext/user-ext.vue';
import UserExtClass from '@/entities/user-ext/user-ext.component';
import UserExtService from '@/entities/user-ext/user-ext.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('UserExt Management Component', () => {
    let wrapper: Wrapper<UserExtClass>;
    let comp: UserExtClass;
    let userExtServiceStub: SinonStubbedInstance<UserExtService>;

    beforeEach(() => {
      userExtServiceStub = sinon.createStubInstance<UserExtService>(UserExtService);
      userExtServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<UserExtClass>(UserExtComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          userExtService: () => userExtServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      userExtServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllUserExts();
      await comp.$nextTick();

      // THEN
      expect(userExtServiceStub.retrieve.called).toBeTruthy();
      expect(comp.userExts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      userExtServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeUserExt();
      await comp.$nextTick();

      // THEN
      expect(userExtServiceStub.delete.called).toBeTruthy();
      expect(userExtServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

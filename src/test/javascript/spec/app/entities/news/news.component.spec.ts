/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import NewsComponent from '@/entities/news/news.vue';
import NewsClass from '@/entities/news/news.component';
import NewsService from '@/entities/news/news.service';

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
  describe('News Management Component', () => {
    let wrapper: Wrapper<NewsClass>;
    let comp: NewsClass;
    let newsServiceStub: SinonStubbedInstance<NewsService>;

    beforeEach(() => {
      newsServiceStub = sinon.createStubInstance<NewsService>(NewsService);
      newsServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<NewsClass>(NewsComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          newsService: () => newsServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      newsServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllNewss();
      await comp.$nextTick();

      // THEN
      expect(newsServiceStub.retrieve.called).toBeTruthy();
      expect(comp.news[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      newsServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeNews();
      await comp.$nextTick();

      // THEN
      expect(newsServiceStub.delete.called).toBeTruthy();
      expect(newsServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});

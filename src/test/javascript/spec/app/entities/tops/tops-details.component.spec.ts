/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TopsDetailComponent from '@/entities/tops/tops-details.vue';
import TopsClass from '@/entities/tops/tops-details.component';
import TopsService from '@/entities/tops/tops.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Tops Management Detail Component', () => {
    let wrapper: Wrapper<TopsClass>;
    let comp: TopsClass;
    let topsServiceStub: SinonStubbedInstance<TopsService>;

    beforeEach(() => {
      topsServiceStub = sinon.createStubInstance<TopsService>(TopsService);

      wrapper = shallowMount<TopsClass>(TopsDetailComponent, { store, i18n, localVue, provide: { topsService: () => topsServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTops = { id: 123 };
        topsServiceStub.find.resolves(foundTops);

        // WHEN
        comp.retrieveTops(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tops).toBe(foundTops);
      });
    });
  });
});

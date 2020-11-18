import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const News = () => import('@/entities/news/news.vue');
// prettier-ignore
const NewsUpdate = () => import('@/entities/news/news-update.vue');
// prettier-ignore
const NewsDetails = () => import('@/entities/news/news-details.vue');
// prettier-ignore
const UserExt = () => import('@/entities/user-ext/user-ext.vue');
// prettier-ignore
const UserExtUpdate = () => import('@/entities/user-ext/user-ext-update.vue');
// prettier-ignore
const UserExtDetails = () => import('@/entities/user-ext/user-ext-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/news',
    name: 'News',
    component: News,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/new',
    name: 'NewsCreate',
    component: NewsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/:newsId/edit',
    name: 'NewsEdit',
    component: NewsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/:newsId/view',
    name: 'NewsView',
    component: NewsDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/news',
    name: 'News',
    component: News,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/new',
    name: 'NewsCreate',
    component: NewsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/:newsId/edit',
    name: 'NewsEdit',
    component: NewsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/:newsId/view',
    name: 'NewsView',
    component: NewsDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/news',
    name: 'News',
    component: News,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/new',
    name: 'NewsCreate',
    component: NewsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/:newsId/edit',
    name: 'NewsEdit',
    component: NewsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/:newsId/view',
    name: 'NewsView',
    component: NewsDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/news',
    name: 'News',
    component: News,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/new',
    name: 'NewsCreate',
    component: NewsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/:newsId/edit',
    name: 'NewsEdit',
    component: NewsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/news/:newsId/view',
    name: 'NewsView',
    component: NewsDetails,
    meta: { authorities: [Authority.USER] },
  },

  {
    path: '/user-ext',
    name: 'UserExt',
    component: UserExt,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/user-ext/new',
    name: 'UserExtCreate',
    component: UserExtUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/user-ext/:userExtId/edit',
    name: 'UserExtEdit',
    component: UserExtUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/user-ext/:userExtId/view',
    name: 'UserExtView',
    component: UserExtDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];

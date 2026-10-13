import { createRouter, createWebHistory } from 'vue-router'
const router = createRouter({ history: createWebHistory(), routes: [
  { path: '/', redirect: '/home' },
  { path: '/home', component: () => import('../views/home/index.vue') },
  { path: '/login', component: () => import('../views/login/index.vue') },
  { path: '/register', component: () => import('../views/register/index.vue') },
  { path: '/job/list', component: () => import('../views/job/list.vue') },
  { path: '/job/detail/:id', component: () => import('../views/job/detail.vue') },
  { path: '/resume', component: () => import('../views/resume/index.vue') },
  { path: '/message', component: () => import('../views/message/index.vue') },
  { path: '/admin', component: () => import('../views/admin/index.vue') }
] })
export default router

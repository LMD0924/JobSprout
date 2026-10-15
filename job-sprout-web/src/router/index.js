import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/user'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'welcome',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('@/components/welcome.vue'),
    },
    { path: '/welcome', name: 'Welcome', component: () => import('@/components/welcome.vue'), meta: { public: true } },
    { path: '/login', name: 'Login', component: () => import('@/views/login/AuthView.vue'), meta: { public: true } },
    { path: '/register', name: 'Register', component: () => import('@/views/register/index.vue'), meta: { public: true } },
    { path: '/home', name: 'Home', component: () => import('@/views/home/index.vue'), meta: { requiresAuth: true } },
    { path: '/profile', name: 'Profile', component: () => import('@/views/profile/index.vue'), meta: { requiresAuth: true } },
    { path: '/resume', name: 'Resume', component: () => import('@/views/PlaceholderPage.vue'), meta: { requiresAuth: true } },
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})

router.beforeEach((to) => {
  const user = useUserStore()
  if (to.meta.requiresAuth && !user.token) return { path: '/login', query: { redirect: to.fullPath } }
  return true
})

export default router

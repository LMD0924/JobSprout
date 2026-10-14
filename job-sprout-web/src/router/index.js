import { createRouter, createWebHistory } from 'vue-router';
import { useUserStore } from '../store/user';
const routes = [
    { path: '/', redirect: '/home' },
    { path: '/login', name: 'Login', component: () => import('../views/login/index.vue'), meta: { public: true, title: '登录' } },
    { path: '/register', name: 'Register', component: () => import('../views/register/index.vue'), meta: { public: true, title: '注册' } },
    { path: '/home', name: 'Home', component: () => import('../views/home/index.vue'), meta: { title: '首页' } },
    { path: '/job/list', name: 'JobList', component: () => import('../views/job/list.vue'), meta: { title: '职位列表' } },
    { path: '/job/detail/:id', name: 'JobDetail', component: () => import('../views/job/detail.vue'), meta: { title: '职位详情' } },
    { path: '/resume', name: 'Resume', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '我的简历', roles: ['STUDENT'] } },
    { path: '/resume/edit', name: 'ResumeEdit', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '编辑简历', roles: ['STUDENT'] } },
    { path: '/apply', name: 'Apply', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '投递记录', roles: ['STUDENT'] } },
    { path: '/message', name: 'Message', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '消息中心', roles: ['STUDENT', 'COMPANY'] } },
    { path: '/company', name: 'Company', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '企业中心', roles: ['COMPANY'] } },
    { path: '/company/job-publish', name: 'JobPublish', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '发布职位', roles: ['COMPANY'] } },
    { path: '/admin', name: 'Admin', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '管理后台', roles: ['ADMIN'] } },
    { path: '/admin/user', name: 'AdminUser', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '用户管理', roles: ['ADMIN'] } },
    { path: '/admin/job', name: 'AdminJob', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '职位审核', roles: ['ADMIN'] } },
    { path: '/admin/stat', name: 'AdminStat', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '数据统计', roles: ['ADMIN'] } },
    { path: '/profile', name: 'Profile', component: () => import('../views/PlaceholderPage.vue'), meta: { title: '个人中心', roles: ['STUDENT', 'COMPANY', 'ADMIN'] } },
    { path: '/:pathMatch(.*)*', name: 'NotFound', component: () => import('../views/PlaceholderPage.vue'), meta: { public: true, title: '页面不存在' } }
];
const router = createRouter({ history: createWebHistory(), routes, scrollBehavior: () => ({ top: 0 }) });
router.beforeEach((to) => { const user = useUserStore(); if (to.meta.public)
    return true; if (!user.token)
    return { path: '/login', query: { redirect: to.fullPath } }; const roles = to.meta.roles; if (roles?.length && !roles.includes(String(user.role)))
    return { path: '/home' }; return true; });
export default router;

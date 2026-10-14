import { computed } from 'vue';
import { Briefcase, ChatDotRound, DataAnalysis, Document, House, User, UserFilled } from '@element-plus/icons-vue';
import { useUserStore } from '../store/user';
const user = useUserStore();
const common = [{ label: '概览', path: '/profile', icon: House }, { label: '个人中心', path: '/profile', icon: User }, { label: '消息中心', path: '/message', icon: ChatDotRound }];
const items = computed(() => { if (user.role === 'ADMIN')
    return [{ label: '后台首页', path: '/admin', icon: DataAnalysis }, { label: '用户管理', path: '/admin/user', icon: UserFilled }, { label: '职位审核', path: '/admin/job', icon: Briefcase }, { label: '数据统计', path: '/admin/stat', icon: DataAnalysis }]; if (user.role === 'COMPANY')
    return [{ label: '企业中心', path: '/company', icon: House }, { label: '发布职位', path: '/company/job-publish', icon: Briefcase }, ...common]; return [{ label: '我的简历', path: '/resume', icon: Document }, { label: '投递记录', path: '/apply', icon: Briefcase }, ...common]; });
debugger; /* PartiallyEnd: #3632/scriptSetup.vue */
const __VLS_ctx = {};
let __VLS_components;
let __VLS_directives;
/** @type {__VLS_StyleScopedClasses['side-link']} */ ;
/** @type {__VLS_StyleScopedClasses['sidebar']} */ ;
/** @type {__VLS_StyleScopedClasses['sidebar']} */ ;
/** @type {__VLS_StyleScopedClasses['sidebar']} */ ;
// CSS variable injection 
// CSS variable injection end 
__VLS_asFunctionalElement(__VLS_intrinsicElements.aside, __VLS_intrinsicElements.aside)({
    ...{ class: "sidebar" },
});
__VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
    ...{ class: "mb-5 px-3 text-xs font-semibold uppercase tracking-wider text-slate-400" },
});
__VLS_asFunctionalElement(__VLS_intrinsicElements.nav, __VLS_intrinsicElements.nav)({
    ...{ class: "space-y-1" },
});
for (const [item] of __VLS_getVForSourceType((__VLS_ctx.items))) {
    const __VLS_0 = {}.RouterLink;
    /** @type {[typeof __VLS_components.RouterLink, typeof __VLS_components.routerLink, typeof __VLS_components.RouterLink, typeof __VLS_components.routerLink, ]} */ ;
    // @ts-ignore
    const __VLS_1 = __VLS_asFunctionalComponent(__VLS_0, new __VLS_0({
        key: (item.path),
        to: (item.path),
        ...{ class: "side-link" },
    }));
    const __VLS_2 = __VLS_1({
        key: (item.path),
        to: (item.path),
        ...{ class: "side-link" },
    }, ...__VLS_functionalComponentArgsRest(__VLS_1));
    __VLS_3.slots.default;
    const __VLS_4 = ((item.icon));
    // @ts-ignore
    const __VLS_5 = __VLS_asFunctionalComponent(__VLS_4, new __VLS_4({
        ...{ class: "h-4 w-4" },
    }));
    const __VLS_6 = __VLS_5({
        ...{ class: "h-4 w-4" },
    }, ...__VLS_functionalComponentArgsRest(__VLS_5));
    (item.label);
    var __VLS_3;
}
/** @type {__VLS_StyleScopedClasses['sidebar']} */ ;
/** @type {__VLS_StyleScopedClasses['mb-5']} */ ;
/** @type {__VLS_StyleScopedClasses['px-3']} */ ;
/** @type {__VLS_StyleScopedClasses['text-xs']} */ ;
/** @type {__VLS_StyleScopedClasses['font-semibold']} */ ;
/** @type {__VLS_StyleScopedClasses['uppercase']} */ ;
/** @type {__VLS_StyleScopedClasses['tracking-wider']} */ ;
/** @type {__VLS_StyleScopedClasses['text-slate-400']} */ ;
/** @type {__VLS_StyleScopedClasses['space-y-1']} */ ;
/** @type {__VLS_StyleScopedClasses['side-link']} */ ;
/** @type {__VLS_StyleScopedClasses['h-4']} */ ;
/** @type {__VLS_StyleScopedClasses['w-4']} */ ;
var __VLS_dollars;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            items: items,
        };
    },
});
export default (await import('vue')).defineComponent({
    setup() {
        return {};
    },
});
; /* PartiallyEnd: #4569/main.vue */

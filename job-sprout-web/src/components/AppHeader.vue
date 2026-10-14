<template>
  <header class="app-header">
    <div class="mx-auto flex h-16 max-w-7xl items-center justify-between px-6">
      <router-link to="/home" class="flex items-center gap-3"><span class="brand-mark">芽</span><span class="text-lg font-extrabold">职芽<span class="text-brand">.</span></span></router-link>
      <nav class="hidden items-center gap-7 text-sm md:flex"><router-link v-for="item in navItems" :key="item.path" :to="item.path" class="nav-link">{{ item.label }}</router-link></nav>
      <div class="flex items-center gap-3"><ThemeToggle /><template v-if="user.token"><el-dropdown @command="handleCommand"><button class="flex items-center gap-2 rounded-xl px-2 py-1.5 hover:bg-brand/5"><el-avatar :size="30" :src="user.userInfo?.avatar">{{ user.userInfo?.nickname?.slice(0,1) || '芽' }}</el-avatar><span class="hidden text-sm sm:block">{{ user.userInfo?.nickname || user.userInfo?.username || '用户' }}</span></button><template #dropdown><el-dropdown-menu><el-dropdown-item command="profile">个人中心</el-dropdown-item><el-dropdown-item command="logout" divided>退出登录</el-dropdown-item></el-dropdown-menu></template></el-dropdown></template><template v-else><router-link to="/login" class="hidden text-sm text-slate-500 hover:text-brand sm:block">登录</router-link><router-link to="/register" class="brand-button text-sm">注册</router-link></template></div>
    </div>
  </header>
</template>
<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import ThemeToggle from './ThemeToggle.vue'
import { useUserStore } from '../store/user'
const router = useRouter(); const user = useUserStore(); const navItems = [{ label: '首页', path: '/home' }, { label: '职位', path: '/job/list' }, { label: '消息', path: '/message' }]
function handleCommand(command: string) { if (command === 'logout') { user.logout(); ElMessage.success('已退出登录'); router.push('/login') } else router.push('/profile') }
</script>
<style scoped>.app-header{position:sticky;top:0;z-index:40;border-bottom:1px solid var(--line);background:var(--nav-bg);backdrop-filter:blur(18px)}.brand-mark{display:inline-flex;width:36px;height:36px;align-items:center;justify-content:center;border-radius:12px;background:linear-gradient(135deg,var(--brand),var(--accent));color:#fff;font-weight:900}.text-brand{color:var(--brand)}.nav-link{color:var(--text-soft);transition:.2s}.nav-link:hover,.router-link-active{color:var(--brand)}</style>

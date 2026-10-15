<template>
  <div class="app-shell min-h-screen">
    <header class="app-topbar glass">
      <div class="mx-auto flex h-[72px] max-w-[1440px] items-center justify-between px-6 lg:px-10">
        <router-link to="/home" class="flex items-center gap-3">
          <span class="brand-symbol btn-brand">芽</span>
          <span class="text-xl font-black tracking-tight">职芽<span class="text-brand">.</span></span>
        </router-link>
        <nav class="hidden items-center gap-8 text-sm font-medium md:flex">
          <router-link to="/home" class="nav-link">工作台</router-link>
          <router-link to="/profile" class="nav-link">个人信息</router-link>
          <router-link to="/resume" class="nav-link">我的简历</router-link>
        </nav>
        <div class="flex items-center gap-3">
          <button class="theme-toggle" aria-label="切换主题" @click="toggleTheme">
            <span v-if="dark">☀</span><span v-else>☾</span>
          </button>
          <el-dropdown @command="handleCommand">
            <button class="flex items-center gap-2 rounded-full px-2 py-1.5 transition hover:bg-black/5 dark:hover:bg-white/10">
              <span class="avatar-badge">{{ user.userInfo?.nickname?.slice(0, 1) || '芽' }}</span>
              <span class="hidden text-sm font-medium sm:block">{{ user.userInfo?.nickname || user.userInfo?.username || '用户' }}</span>
            </button>
            <template #dropdown>
              <el-dropdown-menu><el-dropdown-item command="profile">个人信息</el-dropdown-item><el-dropdown-item command="logout" divided>退出登录</el-dropdown-item></el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </header>
    <main class="mx-auto max-w-[1440px] px-6 py-8 lg:px-10 lg:py-12"><slot /></main>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElNotification } from 'element-plus'
import { useUserStore } from '../stores/user'

const router = useRouter(); const user = useUserStore(); const dark = ref(false)
function setTheme(value) { dark.value = value; document.documentElement.classList.toggle('dark', value); localStorage.setItem('theme', value ? 'dark' : 'light') }
function toggleTheme() { setTheme(!dark.value) }
function handleCommand(command) { if (command === 'logout') { user.logout(); ElNotification({ title: '已退出登录', message: '期待你再次回到职芽', type: 'success' }); router.push('/welcome') } else router.push('/profile') }
onMounted(() => setTheme(localStorage.getItem('theme') === 'dark'))
</script>

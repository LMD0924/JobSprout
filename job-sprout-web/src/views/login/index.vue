<template>
  <div class="login-page min-h-screen lg:grid lg:grid-cols-2">
    <section class="brand-panel relative hidden overflow-hidden lg:flex">
      <div class="brand-orb orb-one"></div><div class="brand-orb orb-two"></div>
      <div class="relative z-10 flex w-full flex-col justify-between p-12 xl:p-16">
        <router-link to="/intro" class="flex items-center gap-3 text-white"><span class="brand-logo">芽</span><span class="text-xl font-extrabold tracking-tight">职芽<span class="text-violet-200">.</span></span></router-link>
        <div class="max-w-xl">
          <span class="mb-6 inline-flex items-center gap-2 rounded-full border border-white/20 bg-white/10 px-4 py-2 text-xs font-medium text-violet-100"><span class="status-dot"></span>2025 春季校园招聘已开启</span>
          <h1 class="mb-6 text-5xl font-black leading-tight text-white xl:text-6xl">让每一颗职芽<br /><span class="text-violet-200">都找到生长的土壤</span></h1>
          <p class="max-w-md text-lg leading-relaxed text-violet-100/80">连接优质企业与校园人才，从简历到 offer，职芽陪你走好求职的每一步。</p>
          <div class="mt-10 grid max-w-md grid-cols-3 gap-4 border-t border-white/15 pt-7 text-white"><div><strong class="text-2xl">12,680+</strong><span class="mt-1 block text-xs text-violet-200/70">在招职位</span></div><div><strong class="text-2xl">3,200+</strong><span class="mt-1 block text-xs text-violet-200/70">合作企业</span></div><div><strong class="text-2xl">96%</strong><span class="mt-1 block text-xs text-violet-200/70">用户满意度</span></div></div>
        </div>
        <p class="text-sm text-violet-200/60">© 2025 JobSprout · 让求职从这里开始</p>
      </div>
    </section>

    <section class="form-panel flex min-h-screen items-center justify-center px-6 py-12 sm:px-10">
      <div class="w-full max-w-md">
        <div class="mb-10 flex items-center justify-between lg:hidden"><router-link to="/intro" class="flex items-center gap-3"><span class="mobile-logo">芽</span><span class="text-xl font-extrabold">职芽<span class="text-brand">.</span></span></router-link><ThemeToggle /></div>
        <div class="mb-8 hidden justify-end lg:flex"><ThemeToggle /></div>
        <div class="mb-8"><p class="mb-3 text-sm font-semibold text-brand">WELCOME BACK</p><h2 class="text-3xl font-black tracking-tight sm:text-4xl">欢迎回到职芽</h2><p class="mt-3 text-sm text-slate-500 dark:text-slate-400">登录后继续探索适合你的职业机会</p></div>
        <el-form ref="formRef" :model="form" :rules="rules" label-position="top" @submit.prevent="submit">
          <el-form-item label="用户名" prop="username"><el-input v-model="form.username" size="large" placeholder="请输入用户名" autocomplete="username"><template #prefix><User /></template></el-input></el-form-item>
          <el-form-item label="密码" prop="password"><el-input v-model="form.password" size="large" type="password" show-password placeholder="请输入密码" autocomplete="current-password" @keyup.enter="submit"><template #prefix><Lock /></template></el-input></el-form-item>
          <div class="mb-6 flex items-center justify-between text-sm"><label class="flex cursor-pointer items-center gap-2 text-slate-500"><input v-model="remember" type="checkbox" class="h-4 w-4 accent-brand" />记住我</label><a href="#" class="text-brand hover:underline" @click.prevent="ElMessage.info('请联系管理员重置密码')">忘记密码？</a></div>
          <el-button class="login-button" :loading="loading" native-type="submit">登录</el-button>
        </el-form>
        <div class="my-8 flex items-center gap-4 text-xs text-slate-400"><span class="h-px flex-1 bg-brand/10"></span><span>还没有账号？</span><span class="h-px flex-1 bg-brand/10"></span></div>
        <router-link to="/register" class="register-button">创建职芽账号</router-link>
        <p class="mt-8 text-center text-xs leading-relaxed text-slate-400">登录即表示你同意<a href="#" class="mx-1 text-brand">用户协议</a>和<a href="#" class="mx-1 text-brand">隐私政策</a></p>
      </div>
    </section>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElNotification } from 'element-plus'
import { Lock, User } from '@element-plus/icons-vue'
import ThemeToggle from '../../components/ThemeToggle.vue'
import { login } from '../../api/auth'
import { useThemeStore } from '../../store/theme'
import { useUserStore } from '../../store/user'

const router = useRouter()
const route = useRoute()
const theme = useThemeStore()
const user = useUserStore()
const formRef = ref()
const loading = ref(false)
const remember = ref(true)
const form = reactive({ username: 'student01', password: '123456' })
const rules = { username: [{ required: true, message: '请输入用户名', trigger: 'blur' }], password: [{ required: true, message: '请输入密码', trigger: 'blur' }, { min: 6, message: '密码至少 6 位', trigger: 'blur' }] }

async function submit() {
  if (!formRef.value || !(await formRef.value.validate().catch(() => false))) return
  loading.value = true
  try {
    const response = await login(form)
    const result = response?.data ?? response
    const token = result?.token ?? result?.data?.token
    const userInfo = (result?.userInfo ?? result?.data?.userInfo)
    if (!token) throw new Error('登录响应缺少 token')
    user.setSession(token, userInfo)
    ElNotification({ title: '登录成功', message: `欢迎回来，${userInfo?.nickname || userInfo?.username || '职芽用户'}`, type: 'success', duration: 2500, customClass: 'sprout-notification' })
    await router.push(String(route.query.redirect || '/intro'))
  } catch (error) {
    ElNotification({ title: '登录失败', message: error?.response?.data?.message || error?.message || '请检查用户名和密码', type: 'error', duration: 3000, customClass: 'sprout-notification' })
  } finally { loading.value = false }
}

onMounted(() => theme.initTheme())
</script>

<style scoped>
.login-page{background:var(--bg);color:var(--text)}.brand-panel{background:linear-gradient(135deg,#4a3bc9 0%,#5b4be0 46%,#7c6bff 100%)}.brand-logo,.mobile-logo{display:inline-flex;height:42px;width:42px;align-items:center;justify-content:center;border-radius:14px;background:rgba(255,255,255,.18);font-size:20px;font-weight:900;color:#fff;box-shadow:0 12px 30px rgba(0,0,0,.18)}.mobile-logo{height:36px;width:36px;border-radius:12px;background:linear-gradient(135deg,var(--brand),var(--accent))}.text-brand{color:var(--brand)}.brand-orb{position:absolute;border-radius:999px;background:rgba(255,255,255,.1);filter:blur(2px)}.orb-one{height:540px;width:540px;right:-180px;top:-160px}.orb-two{height:380px;width:380px;bottom:-170px;left:-130px;background:rgba(167,139,250,.22)}.status-dot{height:6px;width:6px;border-radius:50%;background:#c4b5fd;box-shadow:0 0 0 5px rgba(196,181,253,.16)}.form-panel{background:var(--bg)}:deep(.el-form-item__label){color:var(--text-soft);font-weight:600}:deep(.el-input__wrapper){border-radius:12px;background:var(--card);box-shadow:0 0 0 1px var(--line) inset;padding:3px 14px;transition:.25s}:deep(.el-input__wrapper:hover),:deep(.el-input__wrapper.is-focus){box-shadow:0 0 0 1px var(--brand) inset,0 0 0 4px color-mix(in srgb,var(--brand) 10%,transparent)}:deep(.el-input__inner){color:var(--text)}:deep(.el-input__prefix-inner){color:var(--text-mute)}.login-button{width:100%;height:48px;border:0!important;border-radius:12px!important;background:linear-gradient(135deg,var(--brand),var(--brand-2))!important;color:#fff!important;font-size:15px;font-weight:700;box-shadow:0 14px 28px -12px var(--shadow-brand);transition:.3s}.login-button:hover{transform:translateY(-2px);box-shadow:0 18px 34px -12px var(--shadow-brand)}.register-button{display:flex;height:48px;align-items:center;justify-content:center;border:1px solid var(--line);border-radius:12px;color:var(--brand);font-size:14px;font-weight:700;transition:.25s}.register-button:hover{border-color:var(--b
</style>

<template>
  <div class="auth-page min-h-screen flex items-center justify-center px-4 py-10">
    <!-- 背景装饰 -->
    <div class="auth-decoration auth-decoration-one"></div>
    <div class="auth-decoration auth-decoration-two"></div>

    <!-- 主卡片 -->
    <div class="auth-shell" :class="{ 'is-flipped': isRegister }">
      <!-- 左栏：表单区 -->
      <div class="auth-form-side">
        <!-- 登录表单 -->
        <transition name="fade-slide" mode="out-in">
          <div v-if="!isRegister" key="login" class="auth-form-inner">
            <p class="mb-3 text-sm font-semibold text-brand">SIGN IN</p>
            <h2 class="text-4xl font-black mb-2">欢迎回来</h2>
            <p class="mb-8 muted">登录你的职芽账号，继续职业旅程。</p>

            <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" label-position="top" @submit.prevent="submitLogin">
              <el-form-item label="用户名" prop="username">
                <el-input v-model="loginForm.username" size="large" placeholder="请输入用户名" />
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input v-model="loginForm.password" size="large" type="password" show-password placeholder="请输入密码" />
              </el-form-item>
              <button class="btn-brand auth-submit mt-3 w-full rounded-2xl py-3.5 text-sm font-bold" :disabled="loading" @click.prevent="submitLogin">
                {{ loading ? '登录中…' : '登录' }}
              </button>
            </el-form>
          </div>

          <!-- 注册表单 -->
          <div v-else key="register" class="auth-form-inner">
            <p class="mb-3 text-sm font-semibold text-brand">SIGN UP</p>
            <h2 class="text-4xl font-black mb-2">创建账号</h2>
            <p class="mb-8 muted">加入职芽，开启你的职业旅程。</p>

            <el-form ref="registerFormRef" :model="registerForm" :rules="registerRules" label-position="top" @submit.prevent="submitRegister">
              <el-form-item label="用户名" prop="username">
                <el-input v-model="registerForm.username" size="large" placeholder="请输入用户名" />
              </el-form-item>
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="registerForm.email" size="large" placeholder="请输入邮箱" />
              </el-form-item>
              <el-form-item label="密码" prop="password">
                <el-input v-model="registerForm.password" size="large" type="password" show-password placeholder="请输入密码" />
              </el-form-item>
              <button class="btn-brand auth-submit mt-3 w-full rounded-2xl py-3.5 text-sm font-bold" :disabled="loading" @click.prevent="submitRegister">
                {{ loading ? '注册中…' : '注册' }}
              </button>
            </el-form>
          </div>
        </transition>
      </div>

      <!-- 右栏：介绍 + 切换按钮 -->
      <div class="auth-intro-side">
        <div class="auth-intro-inner">
          <div class="flex items-center gap-3 mb-8">
            <span class="auth-symbol">芽</span>
            <span class="text-xl font-black text-white">职芽<span class="text-violet-200">.</span></span>
          </div>

          <transition name="fade-slide" mode="out-in">
            <div v-if="!isRegister" key="intro-login">
              <h3 class="text-3xl xl:text-4xl font-black text-white leading-tight mb-5">
                还没有账号？<br />
                <span class="auth-gradient">立即加入</span>职芽
              </h3>
              <p class="text-white/70 leading-7 mb-8 max-w-sm">
                创建账号，享受智能职位推荐、在线简历管理、与企业 HR 实时沟通。
              </p>
            </div>
            <div v-else key="intro-register">
              <h3 class="text-3xl xl:text-4xl font-black text-white leading-tight mb-5">
                已有账号？<br />
                <span class="auth-gradient">回到登录</span>继续
              </h3>
              <p class="text-white/70 leading-7 mb-8 max-w-sm">
                登录职芽，继续寻找适合你的机会，或与下一位优秀人才相遇。
              </p>
            </div>
          </transition>

          <button class="btn-intro" @click="toggleMode">
            {{ isRegister ? '去登录' : '去注册' }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElNotification } from 'element-plus'
import { login } from '../../api/auth'   // 按你项目路径改
import { useUserStore } from '../../stores/user'

const router = useRouter()
const route = useRoute()
const user = useUserStore()

const isRegister = ref(false)
const loading = ref(false)

function toggleMode() {
  isRegister.value = !isRegister.value
}

// ---------------- 登录 ----------------
const loginFormRef = ref()
const loginForm = reactive({ username: '', password: '' })
const loginRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少 6 位', trigger: 'blur' },
  ],
}

async function submitLogin() {
  if (!(await loginFormRef.value.validate().catch(() => false))) return
  loading.value = true
  try {
    const result = await login(loginForm)
    const data = result?.data ?? result
    user.setSession(data.token, data.userInfo)
    ElNotification({
      title: '登录成功',
      message: `欢迎回来，${data.userInfo?.nickname || data.userInfo?.username || '职芽用户'}`,
      type: 'success',
    })
    await router.push(String(route.query.redirect || '/home'))
  } catch (error) {
    ElNotification({
      title: '登录失败',
      message: error?.result?.message || error?.response?.data?.message || error?.message || '请检查用户名和密码',
      type: 'error',
    })
  } finally {
    loading.value = false
  }
}

// ---------------- 注册 ----------------
const registerFormRef = ref()
const registerForm = reactive({ username: '', email: '', password: '' })
const registerRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '邮箱格式不正确', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少 6 位', trigger: 'blur' },
  ],
}

async function submitRegister() {
  if (!(await registerFormRef.value.validate().catch(() => false))) return
  loading.value = true
  try {
    // 这里调用你的注册接口，按项目实际情况改
    // const result = await register(registerForm)
    ElNotification({
      title: '注册成功',
      message: `欢迎加入职芽，${registerForm.username}`,
      type: 'success',
    })
    // 注册成功后切回登录
    isRegister.value = false
  } catch (error) {
    ElNotification({
      title: '注册失败',
      message: error?.message || '请稍后重试',
      type: 'error',
    })
  } finally {
    loading.value = false
  }
}
</script>

<style>
/* ============================================================
   登录 / 注册 左右翻转卡片
   亮色：浅灰底 + 深灰边按钮；暗色：保留紫渐变
   ============================================================ */

.auth-page {
  position: relative;
  overflow: hidden;
  background: var(--bg, #F4F5FA);
  color: var(--text, #14172A);
}
html.dark .auth-page {
  background: var(--bg, #000);
  color: var(--text, #F2F3F8);
}

/* 背景光斑 */
.auth-decoration {
  position: absolute;
  border-radius: 50%;
  filter: blur(120px);
  pointer-events: none;
  z-index: 0;
}
.auth-decoration-one {
  width: 42vw; height: 42vw;
  top: -16vw; left: -12vw;
  background: var(--glow-1, rgba(124, 107, 255, .35));
}
.auth-decoration-two {
  width: 36vw; height: 36vw;
  bottom: -14vw; right: -10vw;
  background: var(--glow-2, rgba(91, 75, 224, .25));
}
html.dark .auth-decoration { opacity: .55; }

/* ============ 主卡片 ============ */
.auth-shell {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 960px;
  min-height: 600px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  border-radius: 32px;
  overflow: hidden;
  background: var(--card-solid, #F7F8FB);
  border: 1px solid var(--card-border, rgba(0, 0, 0, .10));
  box-shadow: 0 24px 60px -24px rgba(0, 0, 0, .15);
}

/* 两栏公共布局 */
.auth-form-side,
.auth-intro-side {
  position: relative;
  transition: transform .8s cubic-bezier(.65, 0, .35, 1);
  will-change: transform;
}

/* 表单侧 */
.auth-form-side {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 3rem 3.5rem;
  background: var(--card-solid, #F7F8FB);
  z-index: 2;
}
html.dark .auth-form-side {
  background: #0A0A0F;
}

.auth-form-inner {
  width: 100%;
  max-width: 380px;
}

/* 介绍侧 */
.auth-intro-side {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 3rem;
  background: linear-gradient(150deg, var(--brand, #5B4BE0), var(--brand-2, #4A3BC9), var(--accent, #7C6BFF));
  color: #fff;
  z-index: 1;
  overflow: hidden;
}
html.dark .auth-intro-side {
  background: linear-gradient(150deg, #0A0A0F, #050508 60%, #0A0A1A);
  box-shadow: inset 0 0 0 1px rgba(124, 107, 255, .25);
}

/* 介绍侧里面再加一层装饰光斑，增加层次感 */
.auth-intro-side::before {
  content: "";
  position: absolute;
  width: 60%; height: 60%;
  top: -20%; right: -20%;
  border-radius: 50%;
  background: rgba(255, 255, 255, .18);
  filter: blur(80px);
  pointer-events: none;
}
.auth-intro-side::after {
  content: "";
  position: absolute;
  width: 50%; height: 50%;
  bottom: -18%; left: -18%;
  border-radius: 50%;
  background: rgba(255, 255, 255, .12);
  filter: blur(80px);
  pointer-events: none;
}

.auth-intro-inner {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 360px;
}

/* ============ 翻转：交换左右 ============ */
.auth-shell.is-flipped .auth-form-side {
  transform: translateX(100%);
}
.auth-shell.is-flipped .auth-intro-side {
  transform: translateX(-100%);
}

/* 翻转后，表单侧圆角需要调整（让它们在视觉上无缝接合） */
.auth-shell.is-flipped .auth-form-side {
  border-radius: 0 32px 32px 0;
}
.auth-shell.is-flipped .auth-intro-side {
  border-radius: 32px 0 0 32px;
}

/* 响应式：小屏时上下堆叠，不做左右翻转 */
@media (max-width: 860px) {
  .auth-shell {
    grid-template-columns: 1fr;
    min-height: auto;
    max-width: 520px;
  }
  .auth-form-side,
  .auth-intro-side {
    padding: 2.5rem 1.75rem;
    transform: none !important;
  }
  .auth-intro-side {
    order: -1;
    padding: 2.25rem 1.75rem;
  }
  .auth-shell.is-flipped .auth-form-side,
  .auth-shell.is-flipped .auth-intro-side {
    border-radius: 0;
  }
}

/* ============ 文字与图形 ============ */
.text-brand { color: var(--brand, #5B4BE0); }
.muted { color: var(--text-mute, #969AB0); }

.auth-symbol {
  width: 44px; height: 44px;
  display: inline-flex; align-items: center; justify-content: center;
  border-radius: 14px;
  background: rgba(255, 255, 255, .18);
  border: 1px solid rgba(255, 255, 255, .35);
  color: #fff;
  font-weight: 900;
  backdrop-filter: blur(8px);
}

.auth-gradient {
  background: linear-gradient(120deg, #C4B5FD, #fff, #A78BFA);
  background-size: 200% 200%;
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  animation: authGrad 6s ease infinite;
}
@keyframes authGrad {
  0%,100% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
}

/* ============ 按钮：浅灰底 + 深灰边 + 悬浮 + 气泡 ============ */
.btn-brand {
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  color: var(--brand, #5B4BE0);
  background: #ECEDF2;
  border: 1px solid #C9CBD4;
  box-shadow: 0 2px 6px -2px rgba(0, 0, 0, .08);
  transition:
    transform .3s cubic-bezier(.16,1,.3,1),
    box-shadow .3s cubic-bezier(.16,1,.3,1),
    background .3s,
    border-color .3s,
    color .3s;
  cursor: pointer;
  overflow: hidden;
}
.btn-brand:hover {
  transform: translateY(-3px);
  background: #E3E5EC;
  border-color: #A9ACB8;
  box-shadow: 0 10px 22px -8px rgba(0, 0, 0, .22);
}
.btn-brand:active {
  transform: translateY(-1px) scale(.96);
  background: #DDE0E8;
  border-color: #8E91A0;
  box-shadow: 0 2px 6px -3px rgba(0, 0, 0, .2);
}
.btn-brand:disabled {
  opacity: .6;
  cursor: not-allowed;
  transform: none;
}

/* 暗色：紫渐变 */
html.dark .btn-brand {
  color: #fff;
  background: linear-gradient(135deg, var(--brand, #7C6BFF), var(--brand-2, #6B5AE0));
  border: 1px solid rgba(167, 139, 250, .35);
  box-shadow: 0 0 0 1px rgba(167, 139, 250, .25) inset;
}
html.dark .btn-brand:hover {
  transform: translateY(-3px);
  box-shadow: 0 16px 40px -8px rgba(124, 107, 255, .8),
  0 0 0 1px rgba(167, 139, 250, .6) inset;
}
html.dark .btn-brand:active {
  transform: translateY(-1px) scale(.96);
}

/* ============ 介绍侧切换按钮 ============ */
.btn-intro {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.85rem 2rem;
  border-radius: 16px;
  font-weight: 700;
  font-size: .9rem;
  color: #fff;
  background: rgba(255, 255, 255, .12);
  border: 1px solid rgba(255, 255, 255, .35);
  backdrop-filter: blur(8px);
  transition: all .3s cubic-bezier(.16,1,.3,1);
  cursor: pointer;
}
.btn-intro:hover {
  background: rgba(255, 255, 255, .22);
  transform: translateY(-3px);
  box-shadow: 0 10px 24px -8px rgba(0, 0, 0, .3);
}
.btn-intro:active {
  transform: translateY(-1px) scale(.96);
}

/* 暗色下按钮稍微加点紫光 */
html.dark .btn-intro {
  background: rgba(124, 107, 255, .15);
  border-color: rgba(167, 139, 250, .5);
}
html.dark .btn-intro:hover {
  background: rgba(124, 107, 255, .28);
  box-shadow: 0 10px 24px -8px rgba(124, 107, 255, .7);
}

/* ============ 表单内容切换动画 ============ */
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: opacity .35s ease, transform .35s ease;
}
.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(12px);
}
.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-12px);
}

/* ============ Element Plus 微调 ============ */
.auth-form-inner :deep(.el-form-item__label) {
  color: var(--text-soft, #575C75);
  font-weight: 600;
}
.auth-form-inner :deep(.el-input__wrapper) {
  border-radius: 14px;
  box-shadow: 0 0 0 1px var(--card-border, rgba(0, 0, 0, .10)) inset;
  background: var(--card-solid, #F7F8FB);
}
.auth-form-inner :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px var(--brand, #5B4BE0) inset;
}
html.dark .auth-form-inner :deep(.el-input__wrapper) {
  background: rgba(255, 255, 255, .04);
  box-shadow: 0 0 0 1px rgba(124, 107, 255, .25) inset;
}
</style>

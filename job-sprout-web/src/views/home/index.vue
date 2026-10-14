<template>
  <div class="landing min-h-screen overflow-hidden bg-slate-50 text-slate-900 transition-colors duration-500 dark:bg-black dark:text-slate-100">
    <!-- 背景 -->
    <div class="bg-grid"></div>
    <div class="glow glow-one"></div>
    <div class="glow glow-two"></div>

    <!-- 顶部导航 -->
    <header :class="['fixed inset-x-0 top-0 z-50 border-b transition-all duration-300', scrolled ? 'nav-scrolled' : 'nav-glass']">
      <div class="mx-auto flex h-20 max-w-[1400px] items-center justify-between px-6 lg:px-10">
        <router-link to="/intro" class="flex items-center gap-3">
          <span class="brand-mark flex h-10 w-10 items-center justify-center rounded-2xl text-xl font-black text-white">芽</span>
          <span class="text-xl font-extrabold tracking-tight">职芽<span class="text-brand">.</span></span>
        </router-link>

        <nav class="hidden items-center gap-9 text-sm font-medium md:flex">
          <a href="#jobs" class="nav-link">职位</a>
          <a href="#features" class="nav-link">特色</a>
          <a href="#companies" class="nav-link">企业</a>
          <a href="#about" class="nav-link">关于职芽</a>
        </nav>

        <div class="flex items-center gap-3">
          <button class="theme-toggle" aria-label="切换主题" @click="toggleTheme">{{ dark ? '☀' : '☾' }}</button>
          <template v-if="user.token">
            <span class="hidden text-sm text-slate-500 sm:block dark:text-slate-400">
              你好，{{ user.userInfo?.nickname || user.userInfo?.username }}
            </span>
            <button class="brand-button rounded-full px-5 py-2.5 text-sm font-semibold text-white" @click="goDashboard">
              进入工作台
            </button>
          </template>
          <template v-else>
            <router-link to="/login" class="hidden px-3 text-sm font-medium text-slate-600 transition hover:text-brand sm:block dark:text-slate-300">
              登录
            </router-link>
            <router-link to="/register" class="brand-button rounded-full px-5 py-2.5 text-sm font-semibold text-white">
              开始使用
            </router-link>
          </template>
        </div>
      </div>
    </header>

    <main>
      <!-- Hero -->
      <section class="relative px-6 pb-24 pt-40 lg:px-10 lg:pb-32 lg:pt-48">
        <div class="mx-auto grid max-w-[1400px] items-center gap-16 lg:grid-cols-[1.05fr_1fr] lg:gap-20">
          <!-- 左 -->
          <div>
            <div class="mb-8 inline-flex items-center gap-2.5 rounded-full border border-brand/15 bg-white/70 px-4 py-2 text-xs font-medium shadow-sm backdrop-blur dark:bg-white/[0.04]">
              <span class="pulse-dot"></span>
              <span class="text-slate-500 dark:text-slate-300">2025 春季校园招聘已开启</span>
            </div>

            <h1 class="mb-8 text-5xl font-black leading-[1.06] tracking-tight sm:text-7xl lg:text-[76px]">
              让每一颗<br />
              <span class="gradient-text">职芽</span><br />
              <span class="text-slate-500 dark:text-slate-300">都找到成长的土壤</span>
            </h1>

            <p class="mb-10 max-w-xl text-lg leading-relaxed text-slate-500 dark:text-slate-400">
              职芽 JobSprout 是面向高校学生的在线求职平台。连接优质企业与校园人才，从简历到 offer，陪你走好每一步。
            </p>

            <div class="mb-8 flex max-w-xl items-center gap-2 rounded-2xl border border-brand/15 bg-white/80 p-2 shadow-xl shadow-brand/5 backdrop-blur dark:bg-white/[0.06]">
              <span class="pl-3 text-slate-400">⌕</span>
              <input
                  v-model="keyword"
                  class="min-w-0 flex-1 bg-transparent py-2.5 text-sm outline-none placeholder:text-slate-400"
                  placeholder="搜索职位、公司或关键词…"
                  @keyup.enter="searchJobs"
              />
              <button class="brand-button rounded-xl px-6 py-2.5 text-sm font-semibold text-white" @click="searchJobs">
                搜索
              </button>
            </div>

            <div class="flex flex-wrap items-center gap-3 text-sm text-slate-500 dark:text-slate-400">
              <span>热门：</span>
              <button v-for="tag in hotTags" :key="tag" class="tag" @click="keyword = tag">{{ tag }}</button>
            </div>
          </div>

          <!-- 右：3D 可切换层叠卡片 -->
          <div
              class="relative hidden h-[510px] items-center justify-center lg:flex"
              @mousemove="moveStack"
              @mouseleave="resetStack"
          >
            <div class="stack" :style="stackStyle">
              <!-- 底层 -->
              <div
                  class="stack-card stack-back"
                  :class="{ 'is-active': activeCard === 0 }"
                  @mouseenter="activeCard = 0"
              >
                <JobMini
                    company="阿里巴巴"
                    title="产品经理实习生"
                    city="杭州"
                    color="from-orange-400 to-orange-600"
                    badge="实习"
                    salary="200/天"
                />
              </div>

              <!-- 中层 -->
              <div
                  class="stack-card stack-middle"
                  :class="{ 'is-active': activeCard === 1 }"
                  @mouseenter="activeCard = 1"
              >
                <JobMini
                    company="字节跳动"
                    title="前端开发工程师"
                    city="北京"
                    color="from-violet-500 to-indigo-500"
                    badge="热招"
                    salary="16-28K"
                />
              </div>

              <!-- 顶层 -->
              <div
                  class="stack-card stack-front"
                  :class="{ 'is-active': activeCard === 2 }"
                  @mouseenter="activeCard = 2"
              >
                <JobMini
                    company="腾讯"
                    title="Java 后端开发工程师"
                    city="深圳"
                    color="from-sky-500 to-indigo-600"
                    badge="急招"
                    salary="18-30K"
                />
              </div>
            </div>
          </div>
        </div>
      </section>

      <!-- 数据统计 -->
      <section class="px-6 pb-28 lg:px-10">
        <div class="mx-auto grid max-w-5xl grid-cols-2 gap-5 rounded-3xl border border-brand/10 bg-white/60 p-6 shadow-lg shadow-brand/5 backdrop-blur sm:grid-cols-4 dark:bg-white/[0.04]">
          <div v-for="item in stats" :key="item.label" class="text-center">
            <div class="counter text-3xl font-black text-brand">{{ item.value }}</div>
            <div class="mt-1 text-xs text-slate-500 dark:text-slate-400">{{ item.label }}</div>
          </div>
        </div>
      </section>

      <!-- 热门职位 -->
      <section id="jobs" class="px-6 pb-32 lg:px-10">
        <div class="mx-auto max-w-[1400px]">
          <div class="mb-14 flex items-end justify-between">
            <div>
              <p class="mb-3 text-sm font-semibold text-brand">— 热门职位</p>
              <h2 class="text-4xl font-black tracking-tight sm:text-6xl">找到你的下一站</h2>
            </div>
            <router-link to="/job/list" class="hidden text-sm font-semibold text-brand sm:block">
              查看全部职位 →
            </router-link>
          </div>

          <div class="job-grid">
            <article v-for="job in jobs" :key="job.title" class="job-card" :class="job.layout">
              <div class="mb-6 flex items-start justify-between">
                <div :class="['company-logo bg-gradient-to-br', job.color]">{{ job.logo }}</div>
                <span class="badge">{{ job.badge }}</span>
              </div>
              <h3 class="mb-2 text-2xl font-bold">{{ job.title }}</h3>
              <p class="mb-6 text-sm text-slate-400">{{ job.company }} · {{ job.city }} · {{ job.type }}</p>
              <div class="mb-6 flex flex-wrap gap-2">
                <span v-for="skill in job.skills" :key="skill" class="skill">{{ skill }}</span>
              </div>
              <div class="flex items-end justify-between border-t border-brand/10 pt-6">
                <strong class="text-3xl font-black text-brand">{{ job.salary }}</strong>
                <button class="brand-button rounded-xl px-5 py-2.5 text-sm font-semibold text-white" @click="goJob">
                  投递 →
                </button>
              </div>
            </article>
          </div>
        </div>
      </section>

      <!-- 平台特色 -->
      <section id="features" class="px-6 pb-32 lg:px-10">
        <div class="mx-auto max-w-[1400px]">
          <div class="mb-16 text-center">
            <p class="mb-3 text-sm font-semibold text-brand">— 平台特色</p>
            <h2 class="mb-4 text-4xl font-black sm:text-6xl">为什么选择职芽</h2>
            <p class="text-lg text-slate-500 dark:text-slate-400">从简历到 offer，职芽陪你走好求职的每一步</p>
          </div>

          <div class="grid gap-6 md:grid-cols-3">
            <article v-for="feature in features" :key="feature.num" class="feature-card">
              <div class="mb-6 text-5xl font-black text-brand/25">{{ feature.num }}</div>
              <div class="feature-icon">{{ feature.icon }}</div>
              <h3 class="mb-3 text-xl font-bold">{{ feature.title }}</h3>
              <p class="text-sm leading-relaxed text-slate-500 dark:text-slate-400">{{ feature.desc }}</p>
            </article>
          </div>
        </div>
      </section>

      <!-- 合作企业 -->
      <section id="companies" class="overflow-hidden px-6 pb-32 lg:px-10">
        <div class="mx-auto max-w-[1400px]">
          <div class="mb-14 text-center">
            <p class="mb-3 text-sm font-semibold text-brand">— 合作企业</p>
            <h2 class="text-3xl font-black">已入驻合作企业</h2>
          </div>
          <div class="company-track">
            <span v-for="company in companies" :key="company + '-a'">{{ company }}</span>
            <span v-for="company in companies" :key="company + '-b'">{{ company }}</span>
          </div>
        </div>
      </section>

      <!-- CTA -->
      <section id="about" class="px-6 pb-32 lg:px-10">
        <div class="cta mx-auto max-w-[1400px] overflow-hidden rounded-[40px] px-8 py-20 text-center text-white sm:px-16">
          <h2 class="relative mb-6 text-4xl font-black leading-tight sm:text-6xl">
            准备好开启<br />职业旅程了吗？
          </h2>
          <p class="relative mx-auto mb-10 max-w-xl text-lg text-white/80">
            注册职芽，完善简历，让优质企业主动找到你。
          </p>
          <div class="relative flex flex-wrap justify-center gap-4">
            <router-link to="/register" class="rounded-full bg-white px-10 py-4 font-bold text-brand transition hover:-translate-y-1 hover:shadow-2xl">
              立即注册
            </router-link>
            <router-link to="/job/list" class="rounded-full border border-white/30 bg-white/10 px-10 py-4 font-bold transition hover:bg-white/20">
              浏览职位
            </router-link>
          </div>
        </div>
      </section>
    </main>

    <footer class="px-6 pb-10 lg:px-10">
      <div class="mx-auto max-w-[1400px] border-t border-brand/10 pt-8 text-center text-sm text-slate-400">
        © 2025 职芽 JobSprout · 基于 Spring Boot 的学生在线求职平台 · 毕业设计作品
      </div>
    </footer>
  </div>
</template>

<script setup>
import { computed, defineComponent, h, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../../store/user'

const router = useRouter()
const user = useUserStore()
const keyword = ref('')
const dark = ref(false)
const scrolled = ref(false)
const tiltX = ref(0)
const tiltY = ref(0)
// 当前激活卡片：0 底层、1 中层、2 顶层
const activeCard = ref(2)

const hotTags = ['Java 开发', '前端工程师', '产品经理']
const stats = [
  { value: '12,680+', label: '在招职位' },
  { value: '3,200+', label: '合作企业' },
  { value: '28,900+', label: '注册学生' },
  { value: '96%', label: '满意度' }
]
const companies = ['腾讯', '字节跳动', '阿里巴巴', '百度', '美团', '京东', '网易', '华为']
const features = [
  { num: '01', icon: '✦', title: '智能职位推荐', desc: '基于你的专业、技能与求职意向，精准匹配适合的校招岗位。' },
  { num: '02', icon: '▤', title: '在线简历管理', desc: '一键生成、编辑、投递简历，实时查看投递进度与反馈。' },
  { num: '03', icon: '◌', title: '在线沟通', desc: '与企业 HR 实时沟通，快速了解岗位详情，提升求职效率。' }
]
const jobs = [
  { layout: 'job-a', logo: '腾', color: 'from-sky-500 to-indigo-600', badge: '急招', title: 'Java 后端开发工程师', company: '腾讯', city: '深圳', type: '2025 校招', skills: ['Spring Boot', 'MySQL', 'Redis'], salary: '18-30K' },
  { layout: 'job-b', logo: '字', color: 'from-violet-500 to-indigo-500', badge: '热招', title: '前端开发工程师', company: '字节跳动', city: '北京', type: '2025 校招', skills: ['Vue', 'React', 'TypeScript'], salary: '16-28K' },
  { layout: 'job-c', logo: '阿', color: 'from-orange-400 to-orange-600', badge: '实习', title: '产品经理实习生', company: '阿里巴巴', city: '杭州', type: '实习', skills: ['需求分析', 'Axure'], salary: '200/天' },
  { layout: 'job-d', logo: '美', color: 'from-blue-500 to-indigo-500', badge: '新发布', title: '算法工程师（推荐方向）', company: '美团', city: '北京', type: '2025 校招', skills: ['Python', '推荐系统'], salary: '22-40K' }
]

const stackStyle = computed(() => ({
  transform: `rotateY(${tiltX.value}deg) rotateX(${tiltY.value}deg)`
}))

function toggleTheme() {
  dark.value = !dark.value
  document.documentElement.classList.toggle('dark', dark.value)
  localStorage.setItem('theme', dark.value ? 'dark' : 'light')
}
function goDashboard() {
  router.push(
      user.role === 'STUDENT' ? '/resume' : user.role === 'COMPANY' ? '/company' : '/admin'
  )
}
function searchJobs() {
  router.push({ path: '/job/list', query: { keyword: keyword.value } })
}
function goJob() {
  router.push('/job/list')
}
function moveStack(e) {
  const r = e.currentTarget.getBoundingClientRect()
  tiltX.value = ((e.clientX - r.left) / r.width - 0.5) * 10
  tiltY.value = -((e.clientY - r.top) / r.height - 0.5) * 8
}
function resetStack() {
  tiltX.value = 0
  tiltY.value = 0
  activeCard.value = 2
}

onMounted(() => {
  dark.value =
      localStorage.getItem('theme') === 'dark' ||
      (!localStorage.getItem('theme') && window.matchMedia('(prefers-color-scheme: dark)').matches)
  document.documentElement.classList.toggle('dark', dark.value)
  window.addEventListener('scroll', () => {
    scrolled.value = window.scrollY > 20
  })
})

/* ===== 卡片内部小卡（修复 class 绑定与样式） ===== */
const JobMini = defineComponent({
  props: {
    company: String,
    title: String,
    city: String,
    color: String,
    badge: String,
    salary: String
  },
  setup(props) {
    return () =>
        h('div', { class: 'p-7' }, [
          h('div', { class: 'mb-5 flex items-start justify-between' }, [
            h('div', { class: 'flex items-center gap-3' }, [
              h(
                  'div',
                  {
                    class: [
                      'flex h-11 w-11 items-center justify-center rounded-xl bg-gradient-to-br text-white font-bold',
                      props.color
                    ]
                  },
                  props.company?.slice(0, 1)
              ),
              h('div', [
                h('div', { class: 'text-sm font-bold' }, props.title),
                h('div', { class: 'text-xs text-slate-400' }, `${props.company} · ${props.city}`)
              ])
            ]),
            props.badge
                ? h(
                    'span',
                    {
                      class:
                          'rounded-full bg-brand/12 px-2.5 py-1 text-[11px] font-medium text-brand'
                    },
                    props.badge
                )
                : null
          ]),
          h('div', { class: 'mb-5 flex gap-2' }, [
            h('span', { class: 'skill' }, '全职'),
            h('span', { class: 'skill' }, '校招')
          ]),
          props.salary
              ? h(
                  'div',
                  { class: 'mt-6 flex items-end justify-between border-t border-brand/10 pt-5' },
                  [
                    h('div', { class: 'text-2xl font-black text-brand' }, props.salary),
                    h(
                        'button',
                        { class: 'brand-button rounded-lg px-4 py-2 text-xs font-semibold text-white' },
                        '投递 →'
                    )
                  ]
              )
              : null
        ])
  }
})
</script>

<style scoped>
/* ===== 主题色 ===== */
.text-brand { color: #5b4be0; }
:global(.dark) .text-brand { color: #a78bfa; }

.brand-mark,
.brand-button {
  background: linear-gradient(135deg, #5b4be0, #7c6bff);
}
:global(.dark) .brand-mark,
:global(.dark) .brand-button {
  background: linear-gradient(135deg, #7c6bff, #a78bfa);
  box-shadow: 0 0 0 1px rgba(167, 139, 250, .3) inset;
}
.brand-button {
  transition: transform .3s, box-shadow .3s;
  box-shadow: 0 12px 28px -12px #5b4be0;
}
.brand-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 18px 32px -12px #5b4be0;
}

/* ===== 导航 ===== */
.nav-glass {
  background: rgba(255, 255, 255, .55);
  backdrop-filter: blur(20px) saturate(180%);
  border-color: rgba(91, 75, 224, .1);
}
.nav-scrolled {
  background: rgba(255, 255, 255, .88);
  backdrop-filter: blur(20px);
  border-color: rgba(91, 75, 224, .12);
  box-shadow: 0 4px 32px rgba(0, 0, 0, .08);
}
:global(.dark) .nav-glass,
:global(.dark) .nav-scrolled {
  background: rgba(0, 0, 0, .78);
  border-color: rgba(124, 107, 255, .18);
}
.nav-link {
  color: #575c75;
  transition: color .25s;
}
.nav-link:hover { color: #5b4be0; }
:global(.dark) .nav-link { color: #9da2b8; }
:global(.dark) .nav-link:hover { color: #a78bfa; }

/* ===== 主题切换按钮 ===== */
.theme-toggle {
  width: 40px;
  height: 40px;
  border-radius: 14px;
  border: 1px solid rgba(91, 75, 224, .15);
  background: rgba(255, 255, 255, .7);
  font-size: 18px;
  transition: .3s;
}
.theme-toggle:hover {
  transform: rotate(15deg) scale(1.08);
  color: #5b4be0;
}
:global(.dark) .theme-toggle {
  background: #0a0a0f;
  color: #c4b5fd;
  border-color: rgba(124, 107, 255, .25);
}

/* ===== 渐变文字 ===== */
.gradient-text {
  background: linear-gradient(120deg, #4a3bc9, #5b4be0, #7c6bff, #9b8cff);
  background-size: 300% 300%;
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  animation: gradient 8s ease infinite;
}
:global(.dark) .gradient-text {
  background: linear-gradient(120deg, #7c6bff, #a78bfa, #c4b5fd, #7c6bff);
  background-size: 300% 300%;
  -webkit-background-clip: text;
  background-clip: text;
  filter: drop-shadow(0 0 20px rgba(124, 107, 255, .5));
}
@keyframes gradient {
  0%, 100% { background-position: 0 50%; }
  50% { background-position: 100% 50%; }
}

/* ===== 背景 ===== */
.bg-grid {
  position: fixed;
  inset: 0;
  z-index: -5;
  background-image:
      linear-gradient(rgba(91, 75, 224, .08) 1px, transparent 1px),
      linear-gradient(90deg, rgba(91, 75, 224, .08) 1px, transparent 1px);
  background-size: 64px 64px;
  mask-image: radial-gradient(ellipse 80% 60% at 50% 0%, #000 30%, transparent 90%);
  -webkit-mask-image: radial-gradient(ellipse 80% 60% at 50% 0%, #000 30%, transparent 90%);
}
:global(.dark) .bg-grid {
  background-image:
      linear-gradient(rgba(124, 107, 255, .12) 1px, transparent 1px),
      linear-gradient(90deg, rgba(124, 107, 255, .12) 1px, transparent 1px);
}

.glow {
  position: fixed;
  z-index: -5;
  border-radius: 50%;
  filter: blur(140px);
  pointer-events: none;
}
.glow-one {
  width: 60vw; height: 60vw;
  top: -25vw; left: -15vw;
  background: rgba(124, 107, 255, .28);
  animation: drift 24s ease-in-out infinite;
}
.glow-two {
  width: 50vw; height: 50vw;
  right: -10vw; bottom: -20vw;
  background: rgba(91, 75, 224, .22);
  animation: drift 28s ease-in-out infinite reverse;
}
:global(.dark) .glow-one { background: rgba(124, 107, 255, .18); }
:global(.dark) .glow-two { background: rgba(91, 75, 224, .14); }
@keyframes drift {
  50% { transform: translate(6vw, 4vh) scale(1.1); }
}

/* ===== 小元素 ===== */
.pulse-dot {
  width: 6px; height: 6px;
  border-radius: 50%;
  background: #5b4be0;
  box-shadow: 0 0 0 5px rgba(91, 75, 224, .12);
}
.tag, .skill {
  border: 1px solid rgba(91, 75, 224, .12);
  background: rgba(255, 255, 255, .65);
  border-radius: 999px;
  padding: .4rem .85rem;
  transition: .25s;
}
.tag:hover {
  color: #5b4be0;
  transform: translateY(-2px);
}
.skill {
  border: 0;
  border-radius: .5rem;
  color: #6b7280;
  font-size: .72rem;
}
:global(.dark) .tag,
:global(.dark) .skill {
  background: rgba(255, 255, 255, .06);
  color: #aeb2c5;
}

/* ===== 3D 层叠卡片（可切换） ===== */
.stack {
  position: relative;
  width: 420px;
  height: 440px;
  transform-style: preserve-3d;
  transition: transform .7s cubic-bezier(.16, 1, .3, 1);
}
.stack-card {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 340px;
  margin-left: -170px;
  margin-top: -180px;
  border: 1px solid rgba(91, 75, 224, .14);
  border-radius: 24px;
  background: rgba(255, 255, 255, .85);
  box-shadow: 0 40px 80px -20px rgba(0, 0, 0, .25);
  backdrop-filter: blur(16px);
  transition: transform .7s cubic-bezier(.16, 1, .3, 1), opacity .5s, box-shadow .5s, border-color .5s;
  cursor: pointer;
}
:global(.dark) .stack-card {
  background: #0a0a0f;
  border-color: rgba(124, 107, 255, .18);
  box-shadow: 0 40px 90px -20px rgba(124, 107, 255, .3),
  0 0 0 1px rgba(124, 107, 255, .06) inset;
}

/* 默认位置（未激活） */
.stack-back {
  transform: translate3d(-100px, -40px, -120px) rotateY(16deg) rotateX(6deg) rotateZ(-7deg);
  opacity: .55;
  z-index: 1;
}
.stack-middle {
  transform: translate3d(-10px, -10px, -40px) rotateY(10deg) rotateX(4deg) rotateZ(-3deg);
  opacity: .8;
  z-index: 2;
}
.stack-front {
  transform: translate3d(70px, 60px, 30px) rotateY(6deg) rotateX(2deg) rotateZ(0deg);
  z-index: 3;
}

/* 激活状态：翻到最前，正视，放大 */
.stack-card.is-active {
  transform: translate3d(0, 0, 80px) rotateY(0) rotateX(0) rotateZ(0) scale(1.06);
  opacity: 1;
  z-index: 10;
  border-color: rgba(124, 107, 255, .5);
  box-shadow: 0 50px 100px -20px rgba(124, 107, 255, .5),
  0 0 0 1px rgba(124, 107, 255, .3);
}
:global(.dark) .stack-card.is-active {
  box-shadow: 0 50px 110px -20px rgba(124, 107, 255, .7),
  0 0 0 1px rgba(167, 139, 250, .5),
  0 0 60px -10px rgba(124, 107, 255, .5);
}

/* ===== 职位卡片错位网格 ===== */
.job-grid {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  gap: 24px;
}
.job-card,
.feature-card {
  position: relative;
  border: 1px solid rgba(91, 75, 224, .12);
  border-radius: 24px;
  background: rgba(255, 255, 255, .72);
  padding: 28px;
  backdrop-filter: blur(16px);
  transition: transform .5s, box-shadow .5s, border-color .5s;
}
.job-card:hover,
.feature-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 30px 70px -25px rgba(91, 75, 224, .4);
  border-color: rgba(124, 107, 255, .35);
}
:global(.dark) .job-card,
:global(.dark) .feature-card {
  background: linear-gradient(180deg, rgba(255, 255, 255, .035), rgba(255, 255, 255, .008));
  border-color: rgba(124, 107, 255, .18);
}
:global(.dark) .job-card:hover,
:global(.dark) .feature-card:hover {
  box-shadow: 0 30px 80px -25px rgba(124, 107, 255, .55),
  0 0 0 1px rgba(124, 107, 255, .35);
}

.job-a { grid-column: 1 / 6; }
.job-b { grid-column: 6 / 12; margin-top: 60px; }
.job-c { grid-column: 2 / 8; margin-top: -40px; }
.job-d { grid-column: 8 / 13; margin-top: 20px; }

.company-logo {
  display: flex;
  height: 56px;
  width: 56px;
  align-items: center;
  justify-content: center;
  border-radius: 16px;
  color: #fff;
  font-size: 20px;
  font-weight: 800;
}
.badge {
  border-radius: 999px;
  background: rgba(124, 107, 255, .12);
  padding: 4px 12px;
  font-size: 12px;
  color: #6655dc;
}
:global(.dark) .badge {
  background: rgba(124, 107, 255, .18);
  color: #c4b5fd;
}

.feature-icon {
  display: flex;
  height: 56px;
  width: 56px;
  align-items: center;
  justify-content: center;
  border-radius: 16px;
  background: rgba(91, 75, 224, .1);
  color: #5b4be0;
  font-size: 26px;
  margin-bottom: 24px;
}
:global(.dark) .feature-icon {
  background: rgba(124, 107, 255, .14);
  color: #a78bfa;
}

/* ===== 企业跑马灯 ===== */
.company-track {
  display: flex;
  width: max-content;
  gap: 64px;
  animation: scrollX 32s linear infinite;
  color: #9da2b8;
  font-size: 30px;
  font-weight: 900;
}
.company-track span { white-space: nowrap; }
@keyframes scrollX {
  to { transform: translateX(-50%); }
}

/* ===== CTA ===== */
.cta {
  position: relative;
  background: linear-gradient(135deg, #5b4be0, #4a3bc9, #7c6bff, #9b8cff);
  background-size: 200% 200%;
  animation: gradient 12s ease infinite;
  box-shadow: 0 40px 100px -20px rgba(91, 75, 224, .5);
}
:global(.dark) .cta {
  background: linear-gradient(135deg, #5b4be0, #7c6bff, #a78bfa, #7c6bff);
  background-size: 200% 200%;
  box-shadow: 0 40px 100px -20px rgba(124, 107, 255, .55),
  0 0 0 1px rgba(167, 139, 250, .25) inset;
}

/* ===== 响应式 ===== */
@media (max-width: 1024px) {
  .job-a, .job-b, .job-c, .job-d {
    grid-column: 1 / -1;
    margin-top: 0;
  }
}
@media (max-width: 640px) {
  .job-card { padding: 22px; }
  .company-track { font-size: 24px; gap: 36px; }
}
</style>
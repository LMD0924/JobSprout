<template>
  <!-- 光标 -->
  <div class="cursor-dot" ref="dotRef"></div>
  <div class="cursor-ring" ref="ringRef"></div>

  <!-- 背景 -->
  <div class="bg-grid"></div>
  <div class="bg-glow g1"></div>
  <div class="bg-glow g2"></div>

  <!-- 导航 -->
  <nav
    id="nav"
    class="fixed left-1/2 -translate-x-1/2 z-50 glass"
    :class="{ 'nav-scrolled': scrolled, 'nav-floating': scrolled }"
  >
    <div class="nav-inner max-w-[1400px] mx-auto px-8 h-20 flex items-center justify-between">
      <a href="#" class="flex items-center gap-2.5 group">
        <div class="w-10 h-10 rounded-2xl btn-brand flex items-center justify-center">
          <svg class="w-5 h-5 text-white" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M12 20V10"/><path d="M12 10c0-3 2-5 5-5 0 3-2 5-5 5z"/><path d="M12 13c0-2.5-1.8-4.5-4.5-4.5 0 2.5 1.8 4.5 4.5 4.5z"/>
          </svg>
        </div>
        <span class="text-xl font-bold tracking-tight">职芽<span style="color:var(--brand)">.</span></span>
      </a>

      <div class="hidden md:flex items-center gap-10 text-sm font-medium">
        <a href="#jobs" class="nav-link">职位</a>
        <a href="#features" class="nav-link">特色</a>
        <a href="#companies" class="nav-link">企业</a>
        <a href="#" class="nav-link">校园招聘</a>
      </div>

      <div class="flex items-center gap-3">
        <button class="theme-toggle" @click="toggleTheme" aria-label="切换主题">
          <svg class="icon-moon w-5 h-5" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"/></svg>
          <svg class="icon-sun w-5 h-5" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><circle cx="12" cy="12" r="4"/><path d="M12 2v2M12 20v2M4.93 4.93l1.41 1.41M17.66 17.66l1.41 1.41M2 12h2M20 12h2M6.34 17.66l-1.41 1.41M19.07 4.93l-1.41 1.41"/></svg>
        </button>
        <router-link to="/login" class="nav-link text-sm font-medium px-3">登录</router-link>
        <router-link to="/register" class="magnetic px-5 py-2.5 rounded-full text-sm font-semibold btn-brand">开始使用</router-link>
      </div>
    </div>
  </nav>

  <!-- Hero -->
  <section class="relative pt-44 pb-32 px-8">
    <div class="max-w-[1400px] mx-auto grid lg:grid-cols-[1.1fr_1fr] gap-20 items-center">
      <div>
        <div class="reveal inline-flex items-center gap-2.5 px-4 py-2 rounded-full text-xs font-medium mb-8 card">
          <span class="pulse-dot"></span>
          <span style="color:var(--text-soft)">2025 春季校园招聘已开启</span>
        </div>
        <h1 class="text-[56px] md:text-[76px] font-black leading-[1.05] tracking-tight mb-8">
          <span class="block">
            <span v-for="(c, i) in '让每一颗'" :key="i" class="char" :style="{ animationDelay: (0.05 + i * 0.05) + 's' }">{{ c }}</span>
          </span>
          <span class="block"><span class="grad-text">职芽</span></span>
          <span class="block" style="color:var(--text-soft);font-weight:800;">
            <span v-for="(c, i) in '都找到生长的土壤'" :key="i" class="char" :style="{ animationDelay: (0.3 + i * 0.05) + 's' }">{{ c }}</span>
          </span>
        </h1>
        <p class="reveal text-lg leading-relaxed mb-10 max-w-lg" style="color:var(--text-soft)">
          职芽 JobSprout 是面向高校学生的在线求职平台。连接优质企业与校园人才，从简历到 offer，陪你走好每一步。
        </p>
        <div class="reveal search-glow flex items-center gap-2 p-2 rounded-2xl card max-w-xl mb-8">
          <div class="pl-3" style="color:var(--text-mute)">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><circle cx="11" cy="11" r="7"/><path d="m21 21-4.3-4.3"/></svg>
          </div>
          <input type="text" placeholder="搜索职位、公司或关键词…" class="flex-1 bg-transparent outline-none text-sm py-2.5" style="color:var(--text);cursor:none" />
          <button class="magnetic px-6 py-2.5 rounded-xl text-sm font-semibold btn-brand">搜索</button>
        </div>
        <div class="reveal flex flex-wrap items-center gap-3 text-sm" style="color:var(--text-soft)">
          <span>热门：</span>
          <a href="#" class="hover-tag px-3.5 py-1.5 rounded-full card transition">Java 开发</a>
          <a href="#" class="hover-tag px-3.5 py-1.5 rounded-full card transition">前端工程师</a>
          <a href="#" class="hover-tag px-3.5 py-1.5 rounded-full card transition">产品经理</a>
        </div>
      </div>

      <!-- 3D 卡片 -->
      <div class="stack-scene relative h-[520px] hidden lg:flex items-center justify-center" ref="sceneRef" @mousemove="onSceneMove" @mouseleave="onSceneLeave">
        <div class="stack" ref="stackRef" @click="nextStack">
          <div
            v-for="(card, i) in stackCards"
            :key="card.title"
            class="stack-card"
            :class="stackPosClass(i)"
            :style="{ opacity: stackOpacity(i), zIndex: stackZ(i) }"
          >
            <div class="flex items-center justify-between mb-5">
              <div class="flex items-center gap-3">
                <div class="w-12 h-12 rounded-2xl flex items-center justify-center text-white font-bold" :style="{ background: card.logoBg }">{{ card.logo }}</div>
                <div>
                  <div class="font-bold">{{ card.title }}</div>
                  <div class="text-xs mt-0.5" style="color:var(--text-mute)">{{ card.meta }}</div>
                </div>
              </div>
              <span class="px-2.5 py-1 rounded-full text-xs font-medium" :style="{ background: card.tagBg, color: card.tagColor }">{{ card.tag }}</span>
            </div>

            <div class="flex flex-wrap gap-1.5 mb-5">
              <span v-for="t in card.tags" :key="t" class="px-2.5 py-1 rounded-lg text-xs" style="background:color-mix(in srgb,var(--text-mute) 12%,transparent);color:var(--text-soft)">{{ t }}</span>
            </div>

            <div class="flex items-end justify-between pt-5" style="border-top:1px solid var(--card-border)">
              <div>
                <div class="text-2xl font-black" style="color:var(--brand)">{{ card.salary }}</div>
                <div class="text-xs mt-0.5" style="color:var(--text-mute)">{{ card.footer }}</div>
              </div>
              <button class="magnetic px-4 py-2 rounded-xl text-xs font-semibold btn-brand">立即投递 →</button>
            </div>
          </div>

          <div class="stack-dots">
            <span
              v-for="(c, i) in stackCards"
              :key="'dot-' + i"
              class="stack-dot"
              :class="{ active: i === stackIndex }"
              @click.stop="setStack(i)"
            ></span>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- 数据统计 -->
  <section class="px-8 pb-32">
    <div class="max-w-[1400px] mx-auto grid grid-cols-2 md:grid-cols-4 gap-6">
      <div v-for="s in stats" :key="s.label" class="reveal card rounded-3xl p-8" :class="{ 'md:mt-8': s.offset }">
        <div class="text-4xl md:text-5xl font-black counter mb-2" :data-target="s.value">0</div>
        <div class="text-sm" style="color:var(--text-mute)">{{ s.label }}</div>
      </div>
    </div>
  </section>

  <!-- 热门职位 -->
  <section id="jobs" class="px-8 pb-32">
    <div class="max-w-[1400px] mx-auto">
      <div class="reveal flex flex-wrap items-end justify-between mb-16 gap-6">
        <div>
          <div class="text-sm font-medium mb-3" style="color:var(--brand)">— 精选职位</div>
          <h2 class="text-4xl md:text-6xl font-black tracking-tight">正在招聘</h2>
        </div>
        <a href="#" class="inline-flex items-center gap-2 text-sm font-semibold group" style="color:var(--brand)">查看全部<span class="group-hover:translate-x-1 transition-transform">→</span></a>
      </div>
      <div class="job-grid">
        <div v-for="job in jobs" :key="job.title" class="reveal job-card" :class="job.pos" @mousemove="onTilt($event)" @mouseleave="onTiltLeave($event)">
          <div class="flex items-start justify-between mb-6">
            <div class="w-14 h-14 rounded-2xl flex items-center justify-center text-white font-bold text-lg" :style="{ background: job.logoBg }">{{ job.logo }}</div>
            <span class="px-3 py-1 rounded-full text-xs font-medium" :style="{ background: job.tagBg, color: job.tagColor }">{{ job.tag }}</span>
          </div>
          <h3 class="text-2xl font-bold mb-2">{{ job.title }}</h3>
          <p class="text-sm mb-6" style="color:var(--text-mute)">{{ job.meta }}</p>
          <div class="flex flex-wrap gap-2 mb-6">
            <span v-for="t in job.tags" :key="t" class="px-3 py-1 rounded-lg text-xs" style="background:color-mix(in srgb,var(--text-mute) 12%,transparent);color:var(--text-soft)">{{ t }}</span>
          </div>
          <div class="flex items-end justify-between pt-6" style="border-top:1px solid var(--card-border)">
            <div class="text-3xl font-black" style="color:var(--brand)">{{ job.salary }}</div>
            <button class="magnetic px-5 py-2.5 rounded-xl text-sm font-semibold btn-brand">投递 →</button>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- 平台特色 -->
  <section id="features" class="px-8 pb-32">
    <div class="max-w-[1400px] mx-auto">
      <div class="reveal text-center mb-20">
        <div class="text-sm font-medium mb-3" style="color:var(--brand)">— 平台特色</div>
        <h2 class="text-4xl md:text-6xl font-black tracking-tight mb-4">为什么选择职芽</h2>
        <p class="max-w-2xl mx-auto text-lg" style="color:var(--text-soft)">从简历到 offer，职芽陪你走好求职的每一步</p>
      </div>
      <div class="grid md:grid-cols-3 gap-6">
        <div v-for="(f, i) in features" :key="f.title" class="reveal card rounded-3xl p-10 hover:-translate-y-2 transition-transform duration-500">
          <div class="text-5xl font-black mb-6" :style="{ color: f.color, opacity: .25 }">0{{ i + 1 }}</div>
          <div class="w-14 h-14 rounded-2xl flex items-center justify-center mb-6" :style="{ background: `color-mix(in srgb,${f.color} 12%,transparent)` }">
            <svg class="w-7 h-7" :style="{ color: f.color }" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24"><path :d="f.icon" /></svg>
          </div>
          <h3 class="text-xl font-bold mb-3">{{ f.title }}</h3>
          <p class="text-sm leading-relaxed" style="color:var(--text-soft)">{{ f.desc }}</p>
        </div>
      </div>
    </div>
  </section>

  <!-- 企业 Logo 墙 -->
  <section id="companies" class="px-8 pb-32 overflow-hidden">
    <div class="max-w-[1400px] mx-auto">
      <div class="reveal text-center mb-14">
        <div class="text-sm font-medium mb-3" style="color:var(--brand)">— 合作企业</div>
        <h2 class="text-3xl font-black">已入驻合作企业</h2>
      </div>
      <div class="relative" style="mask-image:linear-gradient(90deg,transparent,#000 15%,#000 85%,transparent);-webkit-mask-image:linear-gradient(90deg,transparent,#000 15%,#000 85%,transparent)">
        <div class="marquee flex gap-16 w-max" style="animation:scrollX 32s linear infinite">
          <div class="flex gap-16 items-center">
            <span v-for="c in companies" :key="c" class="text-3xl font-black whitespace-nowrap" style="color:var(--text-mute)">{{ c }}</span>
          </div>
          <div class="flex gap-16 items-center" aria-hidden="true">
            <span v-for="c in companies" :key="c + '-dup'" class="text-3xl font-black whitespace-nowrap" style="color:var(--text-mute)">{{ c }}</span>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- CTA -->
  <section class="px-8 pb-32">
    <div class="max-w-[1400px] mx-auto">
      <div class="reveal relative overflow-hidden rounded-[40px] p-16 md:p-24 text-center" style="background:linear-gradient(135deg,var(--brand),var(--brand-2),var(--accent),var(--accent-2));background-size:200% 200%;animation:gradMove 12s ease infinite;box-shadow:0 40px 100px -20px var(--shadow-strong)">
        <div class="absolute top-0 left-0 w-96 h-96 rounded-full bg-white/10 blur-3xl -translate-x-1/2 -translate-y-1/2"></div>
        <div class="absolute bottom-0 right-0 w-96 h-96 rounded-full bg-white/10 blur-3xl translate-x-1/2 translate-y-1/2"></div>
        <div class="relative text-white">
          <h2 class="text-4xl md:text-6xl font-black mb-6 leading-tight">准备好开启<br/>职业旅程了吗？</h2>
          <p class="text-white/85 text-lg mb-10 max-w-xl mx-auto">注册职芽，完善简历，让优质企业主动找到你。</p>
          <div class="flex flex-wrap justify-center gap-4">
            <router-link to="/register" class="magnetic px-10 py-4 rounded-full bg-white font-bold hover:shadow-2xl hover:-translate-y-1 transition-all duration-300" style="color:var(--brand)">立即注册</router-link>
            <button class="magnetic px-10 py-4 rounded-full bg-white/10 border border-white/30 font-bold hover:bg-white/20 transition-all duration-300">了解更多</button>
          </div>
        </div>
      </div>
    </div>
  </section>

  <!-- 页脚 -->
  <footer class="px-8 pb-12">
    <div class="max-w-[1400px] mx-auto">
      <div class="divider mb-12"></div>
      <div class="grid md:grid-cols-4 gap-10 mb-12">
        <div>
          <div class="flex items-center gap-2 mb-5">
            <div class="w-9 h-9 rounded-xl btn-brand flex items-center justify-center">
              <svg class="w-4 h-4 text-white" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 20V10"/><path d="M12 10c0-3 2-5 5-5 0 3-2 5-5 5z"/><path d="M12 13c0-2.5-1.8-4.5-4.5-4.5 0 2.5 1.8 4.5 4.5 4.5z"/></svg>
            </div>
            <span class="font-bold">职芽 JobSprout</span>
          </div>
          <p class="text-sm leading-relaxed" style="color:var(--text-mute)">让每一颗职芽，都找到生长的土壤。</p>
        </div>
        <div v-for="col in columns" :key="col.title">
          <h4 class="font-semibold mb-4 text-sm">{{ col.title }}</h4>
          <ul class="space-y-2.5 text-sm" style="color:var(--text-soft)">
            <li v-for="l in col.links" :key="l"><a href="#" class="hover:opacity-80 transition">{{ l }}</a></li>
          </ul>
        </div>
      </div>
      <div class="pt-8 text-center text-sm" style="border-top:1px solid var(--card-border);color:var(--text-mute)">
        © 2025 职芽 JobSprout · 基于 Spring Boot 的学生在线求职平台 · 毕业设计作品
      </div>
    </div>
  </footer>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

// ============ 主题 ============
const isDark = ref(false)
function applyTheme(dark) {
  document.documentElement.classList.toggle('dark', dark)
  localStorage.setItem('theme', dark ? 'dark' : 'light')
}
function toggleTheme() {
  isDark.value = !isDark.value
  applyTheme(isDark.value)
}

// ============ 导航滚动 ============
const scrolled = ref(false)
function onScroll() {
  scrolled.value = window.scrollY > 20
}

// ============ 光标 ============
const dotRef = ref(null)
const ringRef = ref(null)
let mx = 0, my = 0, rx = 0, ry = 0, rafId = null
function onMove(e) {
  mx = e.clientX; my = e.clientY
  if (dotRef.value) dotRef.value.style.transform = `translate(${mx}px, ${my}px) translate(-50%, -50%)`
}
function animateRing() {
  rx += (mx - rx) * 0.18
  ry += (my - ry) * 0.18
  if (ringRef.value) ringRef.value.style.transform = `translate(${rx}px, ${ry}px) translate(-50%, -50%)`
  rafId = requestAnimationFrame(animateRing)
}

// ============ 3D 层叠卡 ============
const sceneRef = ref(null)
const stackRef = ref(null)
function onSceneMove(e) {
  const rect = sceneRef.value.getBoundingClientRect()
  const x = (e.clientX - rect.left) / rect.width - 0.5
  const y = (e.clientY - rect.top) / rect.height - 0.5
  sceneRef.value.style.setProperty('--parallax-x', `${x * 14}deg`)
  sceneRef.value.style.setProperty('--parallax-y', `${-y * 10}deg`)
}
function onSceneLeave() {
  sceneRef.value.style.setProperty('--parallax-x', '0deg')
  sceneRef.value.style.setProperty('--parallax-y', '0deg')
}

const stackIndex = ref(0)
const stackCards = [
  {
    logo: '腾', logoBg: 'linear-gradient(135deg,#5B4BE0,#7C6BFF)',
    title: 'Java 后端开发工程师', meta: '腾讯 · 深圳 · 校招',
    tag: '急招', tagBg: 'color-mix(in srgb,var(--brand) 15%,transparent)', tagColor: 'var(--brand)',
    tags: ['Spring Boot', 'MySQL', 'Redis'], salary: '18-30K', footer: '已投递 128 人',
  },
  {
    logo: '字', logoBg: 'linear-gradient(135deg,#7C6BFF,#9B8CFF)',
    title: '前端开发工程师', meta: '字节跳动 · 北京 · 校招',
    tag: '热招', tagBg: 'color-mix(in srgb,var(--accent) 15%,transparent)', tagColor: 'var(--accent)',
    tags: ['Vue', 'React', 'TypeScript'], salary: '16-28K', footer: '已投递 96 人',
  },
  {
    logo: '阿', logoBg: 'linear-gradient(135deg,#f59e0b,#f97316)',
    title: '产品经理实习生', meta: '阿里巴巴 · 杭州 · 实习',
    tag: '实习', tagBg: 'color-mix(in srgb,#f59e0b 15%,transparent)', tagColor: '#f59e0b',
    tags: ['需求分析', 'Axure'], salary: '200/天', footer: '已投递 210 人',
  },
]
function nextStack() { stackIndex.value = (stackIndex.value + 1) % stackCards.length }
function setStack(i) { stackIndex.value = i }
function relPos(i) {
  const n = stackCards.length
  return (i - stackIndex.value + n) % n
}
function stackPosClass(i) { return ['pos-top', 'pos-mid', 'pos-back'][relPos(i)] }
function stackOpacity(i) { return [1, 0.85, 0.55][relPos(i)] }
function stackZ(i) { return [3, 2, 1][relPos(i)] }

let stackTimer = null
function startStackAuto() { stackTimer = setInterval(nextStack, 3800) }
function stopStackAuto() { if (stackTimer) { clearInterval(stackTimer); stackTimer = null } }

// ============ 职位卡 3D ============
function onTilt(e) {
  const card = e.currentTarget
  const rect = card.getBoundingClientRect()
  const x = (e.clientX - rect.left) / rect.width - 0.5
  const y = (e.clientY - rect.top) / rect.height - 0.5
  card.style.transform = `perspective(1000px) rotateY(${x * 6}deg) rotateX(${-y * 6}deg) translateY(-10px) scale(1.02)`
}
function onTiltLeave(e) { e.currentTarget.style.transform = '' }

// ============ 数据 ============
const stats = [
  { value: 1286, label: '在招职位', offset: false },
  { value: 520, label: '合作企业', offset: true },
  { value: 38000, label: '注册学生', offset: false },
  { value: 92, label: '就业成功率(%)', offset: true },
]

const jobs = [
  { pos: 'a', logo: '腾', logoBg: 'linear-gradient(135deg,#5B4BE0,#7C6BFF)', tag: '急招', tagBg: 'color-mix(in srgb,var(--brand) 15%,transparent)', tagColor: 'var(--brand)', title: 'Java 后端开发工程师', meta: '腾讯 · 深圳 · 2025 校招', tags: ['Spring Boot', 'MySQL', 'Redis'], salary: '18-30K' },
  { pos: 'b', logo: '字', logoBg: 'linear-gradient(135deg,#7C6BFF,#9B8CFF)', tag: '热招', tagBg: 'color-mix(in srgb,var(--accent) 15%,transparent)', tagColor: 'var(--accent)', title: '前端开发工程师', meta: '字节跳动 · 北京 · 2025 校招', tags: ['Vue', 'React', 'TypeScript'], salary: '16-28K' },
  { pos: 'c', logo: '阿', logoBg: 'linear-gradient(135deg,#f59e0b,#f97316)', tag: '实习', tagBg: 'color-mix(in srgb,#f59e0b 15%,transparent)', tagColor: '#f59e0b', title: '产品经理实习生', meta: '阿里巴巴 · 杭州 · 实习', tags: ['需求分析', 'Axure'], salary: '200/天' },
  { pos: 'd', logo: '美', logoBg: 'linear-gradient(135deg,#3b82f6,#6366f1)', tag: '新发布', tagBg: 'color-mix(in srgb,#3b82f6 15%,transparent)', tagColor: '#3b82f6', title: '算法工程师（推荐方向）', meta: '美团 · 北京 · 2025 校招', tags: ['Python', '推荐系统'], salary: '22-40K' },
]

const features = [
  { title: '智能职位推荐', desc: '基于你的专业、技能与求职意向，精准匹配适合的校招岗位。', color: 'var(--brand)', icon: 'M12 2v20M2 12h20' },
  { title: '在线简历管理', desc: '一键生成、编辑、投递简历，实时查看投递进度与反馈。', color: 'var(--accent)', icon: 'M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z M14 2v6h6' },
  { title: '在线沟通', desc: '与企业 HR 实时沟通，快速了解岗位详情，提升求职效率。', color: '#8b5cf6', icon: 'M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z' },
]

const companies = ['腾讯', '字节跳动', '阿里巴巴', '百度', '美团', '京东', '网易', '华为']

const columns = [
  { title: '产品', links: ['职位搜索', '简历管理', '企业入驻'] },
  { title: '关于', links: ['关于我们', '联系方式', '加入我们'] },
  { title: '帮助', links: ['常见问题', '隐私政策', '用户协议'] },
]

// ============ 生命周期 ============
onMounted(() => {
  const saved = localStorage.getItem('theme')
  const prefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches
  isDark.value = saved === 'dark' || (!saved && prefersDark)
  applyTheme(isDark.value)

  window.addEventListener('scroll', onScroll)
  window.addEventListener('mousemove', onMove)
  animateRing()

  document.querySelectorAll('a, button, .job-card, .stack-card, input').forEach(el => {
    el.addEventListener('mouseenter', () => ringRef.value?.classList.add('hover'))
    el.addEventListener('mouseleave', () => ringRef.value?.classList.remove('hover'))
  })

  const revealObs = new IntersectionObserver((entries) => {
    entries.forEach((entry, i) => {
      if (entry.isIntersecting) {
        setTimeout(() => entry.target.classList.add('show'), i * 80)
        revealObs.unobserve(entry.target)
      }
    })
  }, { threshold: 0.1 })
  document.querySelectorAll('.reveal').forEach(el => revealObs.observe(el))

  const counterObs = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        const el = entry.target
        const target = +el.dataset.target
        const duration = 1800
        const start = performance.now()
        const step = (now) => {
          const p = Math.min((now - start) / duration, 1)
          const eased = 1 - Math.pow(1 - p, 3)
          el.textContent = Math.floor(eased * target).toLocaleString()
          if (p < 1) requestAnimationFrame(step)
          else el.textContent = target.toLocaleString()
        }
        requestAnimationFrame(step)
        counterObs.unobserve(el)
      }
    })
  }, { threshold: 0.5 })
  document.querySelectorAll('.counter').forEach(el => counterObs.observe(el))

  document.querySelectorAll('.magnetic').forEach(btn => {
    btn.addEventListener('mousemove', e => {
      const rect = btn.getBoundingClientRect()
      const x = e.clientX - rect.left
      const y = e.clientY - rect.top
      const cx = rect.width / 2
      const cy = rect.height / 2
      btn.style.transform = `translate(${(x - cx) * 0.25}px, ${(y - cy) * 0.35}px)`
      btn.style.setProperty('--mx', x + 'px')
      btn.style.setProperty('--my', y + 'px')
    })
    btn.addEventListener('mouseleave', () => { btn.style.transform = '' })
  })

  startStackAuto()
  sceneRef.value?.addEventListener('mouseenter', stopStackAuto)
  sceneRef.value?.addEventListener('mouseleave', startStackAuto)
})

onBeforeUnmount(() => {
  window.removeEventListener('scroll', onScroll)
  window.removeEventListener('mousemove', onMove)
  cancelAnimationFrame(rafId)
  stopStackAuto()
})
</script>





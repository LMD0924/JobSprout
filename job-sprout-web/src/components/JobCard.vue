<template>
  <article class="job-card">
    <div class="flex items-start justify-between gap-4"><div class="flex min-w-0 items-center gap-3"><div class="company-logo">{{ job.companyLogo || job.companyName?.slice(0, 1) || '企' }}</div><div class="min-w-0"><h3 class="truncate text-base font-bold">{{ job.title }}</h3><p class="truncate text-sm text-slate-500">{{ job.companyName }}</p></div></div><button class="favorite" :class="{ active: favorite }" title="收藏" @click="favorite = !favorite"><StarFilled /></button></div>
    <div class="mt-5 flex flex-wrap gap-2"><span class="salary">{{ job.salary || '面议' }}</span><span class="meta">{{ job.city || '不限地点' }}</span><span class="meta">{{ job.education || '学历不限' }}</span><span v-for="tag in (job.tags || []).slice(0, 3)" :key="tag" class="tag">{{ tag }}</span></div>
    <div class="mt-5 flex items-center justify-between border-t border-brand/10 pt-4"><span class="text-xs text-slate-400">{{ job.createTime || '刚刚发布' }}</span><button class="brand-button text-sm" @click="emit('apply', job)">投递职位</button></div>
  </article>
</template>
<script setup>
import { ref } from 'vue'
import { StarFilled } from '@element-plus/icons-vue'
defineProps({ job: { type: Object, required: true } })
const emit = defineEmits(['apply'])
const favorite = ref(false)
</script>
<style scoped>.job-card{border:1px solid var(--line);border-radius:20px;background:var(--card);padding:20px;transition:.3s}.job-card:hover{transform:translateY(-4px);box-shadow:0 18px 40px -24px var(--shadow-brand)}.company-logo{display:flex;height:44px;width:44px;flex:none;align-items:center;justify-content:center;border-radius:12px;background:linear-gradient(135deg,var(--brand),var(--accent));color:#fff;font-weight:800}.favorite{color:var(--text-mute);transition:.2s}.favorite.active,.favorite:hover{color:var(--brand)}.salary{font-size:14px;font-weight:700;color:var(--brand)}.meta,.tag{border-radius:8px;background:color-mix(in srgb,var(--text-mute) 10%,transparent);padding:4px 8px;font-size:12px;color:var(--text-soft)}</style>

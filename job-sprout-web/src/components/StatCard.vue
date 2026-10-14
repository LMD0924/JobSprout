<template><div class="surface-card"><div class="flex items-start justify-between"><span class="text-sm text-slate-500">{{ title }}</span><span v-if="trend" :class="trendUp ? 'text-emerald-500' : 'text-rose-500'" class="text-xs font-semibold">{{ trendUp ? '↗' : '↘' }} {{ trend }}</span></div><div class="mt-3 text-3xl font-black text-brand">{{ value }}</div></div></template>
<script setup lang="ts">
withDefaults(defineProps<{ title: string; value: string | number; trend?: string; trendUp?: boolean }>(), { trendUp: true })
</script>

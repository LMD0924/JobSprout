<template>
  <div class="space-y-4">
    <div v-for="i in count" :key="i" class="animate-pulse rounded-[20px] border border-brand/10 bg-[var(--card)] p-5">
      <template v-if="type === 'list'"><div class="mb-4 h-5 w-1/3 rounded bg-slate-200 dark:bg-slate-800"></div><div class="h-3 w-2/3 rounded bg-slate-200 dark:bg-slate-800"></div><div class="mt-4 h-3 w-1/2 rounded bg-slate-200 dark:bg-slate-800"></div></template>
      <template v-else><div class="h-24 rounded bg-slate-200 dark:bg-slate-800"></div></template>
    </div>
  </div>
</template>
<script setup lang="ts">
withDefaults(defineProps<{ type?: 'card' | 'list'; count?: number }>(), { type: 'card', count: 3 })
</script>

<template><div class="mx-auto max-w-4xl py-16"><div class="surface-card text-center"><h1 class="text-2xl font-black">{{ title }}</h1><p class="mt-3 text-slate-500">页面将在下一批实现，当前路由已就绪。</p></div></div></template>
<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute()
const title = computed(() => String(route.meta.title || '职芽 JobSprout'))
</script>

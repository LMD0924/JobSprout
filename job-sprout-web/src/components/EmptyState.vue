<template>
  <div class="flex flex-col items-center justify-center py-16 text-center">
    <component :is="icon" class="mb-4 h-12 w-12 text-slate-300" />
    <h3 class="mb-2 text-base font-semibold">{{ title }}</h3>
    <p class="mb-5 text-sm text-slate-400">{{ description }}</p>
    <button v-if="actionText" class="brand-button text-sm" @click="emit('action')">{{ actionText }}</button>
  </div>
</template>
<script setup lang="ts">
import { Search } from '@element-plus/icons-vue'
withDefaults(defineProps<{ title?: string; description?: string; actionText?: string; icon?: any }>(), { title: '暂无数据', description: '这里暂时没有内容', icon: Search })
const emit = defineEmits<{ action: [] }>()
</script>

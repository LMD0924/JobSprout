<template>
  <div class="mb-7 flex flex-wrap items-end justify-between gap-4">
    <div><h1 class="text-2xl font-black tracking-tight sm:text-3xl">{{ title }}</h1><p v-if="description" class="mt-2 text-sm text-slate-500">{{ description }}</p></div>
    <button v-if="actionText" class="brand-button" @click="emit('action')">{{ actionText }}</button>
  </div>
</template>
<script setup>
defineProps({ title: String, description: String, actionText: String })
const emit = defineEmits(['action'])
</script>

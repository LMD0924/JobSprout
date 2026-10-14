<template>
  <div class="min-h-screen" style="background: var(--bg); color: var(--text)">
    <AppHeader />
    <div class="dashboard-body"><AppSidebar /><main class="min-w-0 flex-1 p-6 lg:p-8"><slot /></main></div>
  </div>
</template>
<script setup lang="ts">
import AppHeader from './AppHeader.vue'
import AppSidebar from './AppSidebar.vue'
</script>
<style scoped>.dashboard-body{display:flex;min-height:calc(100vh - 64px)}@media(max-width:768px){.dashboard-body{display:block}}</style>

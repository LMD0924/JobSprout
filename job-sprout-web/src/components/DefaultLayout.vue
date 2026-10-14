<template>
  <div class="min-h-screen" style="background: var(--bg); color: var(--text)">
    <AppHeader />
    <main><slot /></main>
    <AppFooter />
  </div>
</template>
<script setup>
import AppHeader from './AppHeader.vue'
import AppFooter from './AppFooter.vue'
</script>

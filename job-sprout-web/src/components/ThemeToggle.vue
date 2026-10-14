<template><button class="theme-toggle" type="button" :aria-label="theme.isDark ? '切换浅色主题' : '切换深色主题'" @click="theme.toggleTheme"><Sunny v-if="theme.isDark" /><Moon v-else /></button></template>
<script setup>
import { Moon, Sunny } from '@element-plus/icons-vue'
import { useThemeStore } from '../store/theme'
const theme = useThemeStore()
</script>
<style scoped>.theme-toggle{display:inline-flex;width:40px;height:40px;align-items:center;justify-content:center;border:1px solid var(--line);border-radius:12px;background:var(--card);color:var(--text);cursor:pointer;transition:.3s}.theme-toggle:hover{color:var(--brand);transform:rotate(10deg) scale(1.05)}</style>

import { defineStore } from 'pinia';
import { ref } from 'vue';
export const useThemeStore = defineStore('theme', () => {
    const isDark = ref(localStorage.getItem('theme') === 'dark');
    function applyTheme() { document.documentElement.classList.toggle('dark', isDark.value); localStorage.setItem('theme', isDark.value ? 'dark' : 'light'); }
    function toggleTheme() { isDark.value = !isDark.value; applyTheme(); }
    function initTheme() { const saved = localStorage.getItem('theme'); isDark.value = saved ? saved === 'dark' : window.matchMedia('(prefers-color-scheme: dark)').matches; applyTheme(); }
    return { isDark, toggleTheme, initTheme };
});

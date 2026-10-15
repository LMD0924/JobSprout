import { computed, ref } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))
  const role = computed(() => userInfo.value?.role || '')

  function setSession(nextToken, info) {
    token.value = nextToken || ''
    userInfo.value = info || null
    if (nextToken) localStorage.setItem('token', nextToken)
    if (info) localStorage.setItem('userInfo', JSON.stringify(info))
  }
  function logout() {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }
  return { token, userInfo, role, setSession, logout }
})

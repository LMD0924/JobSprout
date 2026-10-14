import { defineStore } from 'pinia';
import { computed, ref } from 'vue';
export const useUserStore = defineStore('user', () => {
    const token = ref(localStorage.getItem('token') || '');
    const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'));
    const role = computed(() => userInfo.value?.role || localStorage.getItem('role') || '');
    function setSession(newToken, info) { token.value = newToken; localStorage.setItem('token', newToken); if (info) {
        userInfo.value = info;
        localStorage.setItem('userInfo', JSON.stringify(info));
        if (info.role)
            localStorage.setItem('role', info.role);
    } }
    function setToken(newToken) { setSession(newToken); }
    async function login(payload) { setSession(payload.token, payload.userInfo); }
    async function getUserInfo() { return userInfo.value; }
    function logout() { token.value = ''; userInfo.value = null; localStorage.removeItem('token'); localStorage.removeItem('userInfo'); localStorage.removeItem('role'); }
    return { token, userInfo, role, login, getUserInfo, setSession, setToken, logout };
});

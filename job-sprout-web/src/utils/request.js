import axios from 'axios';
import router from '../router';
const request = axios.create({ baseURL: '/api', timeout: 10000, headers: { 'Content-Type': 'application/json' } });
request.interceptors.request.use((config) => { const token = localStorage.getItem('token'); if (token)
    config.headers.Authorization = `Bearer ${token}`; return config; });
request.interceptors.response.use((response) => response.data, async (error) => { if (error.response?.status === 401) {
    localStorage.removeItem('token');
    localStorage.removeItem('userInfo');
    if (router.currentRoute.value.path !== '/login')
        await router.push('/login');
} return Promise.reject(error); });
/** 暂未接入后端时，为页面 API 提供可控的 Promise mock。 */
export function mockRequest(data, delay = 240) { return new Promise(resolve => window.setTimeout(() => resolve(data), delay)); }
export default request;

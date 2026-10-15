import axios from 'axios'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  timeout: 10000,
  headers: { 'Content-Type': 'application/json' },
})

request.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  if (token) config.headers.Authorization = `Bearer ${token}`
  return config
})

request.interceptors.response.use(
  (response) => {
    const result = response.data
    if (result && typeof result.code === 'number' && result.code !== 200) {
      return Promise.reject(Object.assign(new Error(result.message || '请求失败'), { response, result }))
    }
    return result
  },
  (error) => Promise.reject(error),
)

export default request

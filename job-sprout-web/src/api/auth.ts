import request from '../utils/request'
export const login = (data: { username: string; password: string }) => request.post('/auth/login', data)
export const register = (data: Record<string, string>) => request.post('/auth/register', data)

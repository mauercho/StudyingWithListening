import axios from 'axios'

const api = axios.create({
  baseURL: 'https://k11a304.p.ssafy.io/api/',
  timeout: 60000,
})

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

api.interceptors.response.use(
  (response) => {
    return response.data
  },
  (error) => {
    if (error.response) {
      if (error.response.status === 401) {
        window.location.href = '/login'
      }
      return Promise.reject(error.response.data)
    } else if (error.request) {
      console.error('No response received from server')
      return Promise.reject(error)
    } else {
      console.error('Error', error.message)
      return Promise.reject(error)
    }
  }
)

export default api

import { getCookie } from 'cookies-next'

export const getClientToken = () => {
  return getCookie('accessToken') || null
}

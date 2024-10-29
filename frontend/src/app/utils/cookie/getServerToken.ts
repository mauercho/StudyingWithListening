'use server'

import { getCookie } from 'cookies-next'
import { cookies } from 'next/headers'

export const getServerToken = () => {
  return getCookie('accessToken', { cookies }) || null
}

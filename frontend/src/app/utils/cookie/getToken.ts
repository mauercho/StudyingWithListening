import { getClientToken } from '@/app/utils/cookie/getClientToken'
import { getServerToken } from '@/app/utils/cookie/getServerToken'

export const getToken = () => {
  if (typeof window === 'undefined') {
    return getServerToken()
  } else {
    return getClientToken()
  }
}

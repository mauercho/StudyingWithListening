import { ApiError } from '@/app/utils/customError'
import { getToken } from '@/app/utils/cookie/getToken'

export const postSentenceImage = async (url: string, payload: FormData) => {
  try {
    const accessToken = getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'POST',
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
      body: payload,
    })

    if (!response.ok) {
      throw new ApiError(
        response.status,
        `Network response was not ok: ${response.status} ${response.statusText}`
      )
    }
    const data = await response.json()
    return { data, status: response.status }
  } catch (error) {
    console.error(error)
  }
}

interface UserSentence {
  sentence_text: string
}

export const postUserSentence = async (
  url: string,
  payload: UserSentence
): Promise<any> => {
  try {
    const accessToken = await getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
      body: JSON.stringify(payload),
    })
    if (!response.ok) {
      throw new ApiError(
        response.status,
        `Network response was not ok: ${response.status} ${response.statusText}`
      )
    }

    const data = await response.json()
    return { data, status: response.status }
  } catch (error: any) {
    console.error(error)
    throw error
  }
}

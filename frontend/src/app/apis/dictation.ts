import { ApiError } from '@/app/utils/customError'
import { getToken } from '@/app/utils/cookie/getToken'

export const getDictationSentences = async (url: string): Promise<any> => {
  try {
    const accessToken = await getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
    })

    if (!response.ok) {
      throw new ApiError(
        response.status,
        `Network response was not ok: ${response.status} ${response.statusText}`
      )
    }

    const data = await response.json()
    return data
  } catch (error: any) {
    console.error(error)
    throw error
  }
}

interface DictationGrading {
  toggled: boolean
  sentence_id: number
  user_sentence: string
}

export const postDictationGrading = async (
  url: string,
  payload: DictationGrading[]
): Promise<any> => {
  try {
    const accessToken = getToken()
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
    return {
      error,
      status: error.status,
    }
  }
}

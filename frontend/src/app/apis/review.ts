import { ApiError } from '@/app/utils/customError'
import { getToken } from '@/app/utils/cookie/getToken'


export interface SentenceContent {
  review_sentence_id: number
  sentence_id: number
  topic_category: string
  sentence_text: string
}

export const getReviewSentence = async (
  url: string,
): Promise<any> => {
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

export const postReviewSentenceSave = async (
  url: string,
  sentenceId: number,
): Promise<any> => {
  try {
    const accessToken = await getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
      body: JSON.stringify({
        sentence_id: sentenceId,
      }),
    })
    if (!response.ok) {
      throw new ApiError(
        response.status,
        `Network response was not ok: ${response.status} ${response.statusText}`
      )
    }
  } catch (error: any) {
    console.error(error)
    throw error
  }
}

export const deleteReviewSentence = async (
  url: string,
  reviewSentenceId: number,
): Promise<any> => {
  try {
    const accessToken = await getToken()
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}${url}/${reviewSentenceId}`,
      {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`,
        },
      }
    )
    if (!response.ok) {
      throw new ApiError(
        response.status,
        `Network response was not ok: ${response.status} ${response.statusText}`
      )
    }
  } catch (error: any) {
    console.error(error)
    throw error
  }
}

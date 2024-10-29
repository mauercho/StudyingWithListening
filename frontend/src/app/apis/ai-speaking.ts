import { getToken } from '@/app/utils/cookie/getToken'

export interface AIConversationCard {
  situation_detail: string
  situation_id: number
  situation_img_url: string
  situation_place: string
  situation_title: string
  topic_category: '일상' | '행정' | '교육'
}

interface AISpeakingResponse {
  message: string
  user_role: string
  ai_role: string
}
interface AISpeakingRequestBody {
  situation_id: number
  message: string
}

export const getAIConversationList = async (
  url: string
): Promise<{ data: AIConversationCard[]; status: number } | undefined> => {
  try {
    const accessToken = getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
    })

    if (!response.ok) {
      console.log(response)
    }
    const data = await response.json()
    return { data: data, status: response.status }
  } catch (error) {
    console.error('There was a problem with the fetch operation:', error)
  }
}

export const getStartAISpeaking = async (
  url: string
): Promise<{ data: AISpeakingResponse; status: number } | undefined> => {
  try {
    const accessToken = getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
    })

    if (!response.ok) {
      console.log(response)
    }
    const data = await response.json()
    return { data: data, status: response.status }
  } catch (error) {
    console.error('There was a problem with the fetch operation:', error)
  }
}

export const getEndAISpeaking = async (
  url: string
): Promise<
  { data: { message: string; leaves: number }; status: number } | undefined
> => {
  try {
    const accessToken = getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
    })

    if (!response.ok) {
      console.log(response)
    }
    const data = await response.json()
    return { data: data, status: response.status }
  } catch (error) {
    console.error('There was a problem with the fetch operation:', error)
  }
}

export const postSendAISpeakingMessage = async (
  url: string,
  payload: AISpeakingRequestBody
): Promise<{ data: AISpeakingResponse; status: number } | undefined> => {
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
      console.log(response)
    }
    const data = await response.json()
    return { data: data, status: response.status }
  } catch (error) {
    console.error('There was a problem with the fetch operation:', error)
  }
}

import { ApiError } from '@/app/utils/customError'
import { getToken } from '@/app/utils/cookie/getToken'

interface Lecture {
  lecture_id: number
  teacher_name: string
  lecture_title: string
  lecture_img_url: string
  lecture_note_count: number
}

interface LectureList {
  lecture_list: Lecture[]
}

export const getLectureList = async (url: string): Promise<LectureList> => {
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
    throw error
  }
}

interface LectureNote {
  note_id: number
  note_title: string
  note_content: string
  note_created_at: string
}

export const getLectureNote = async (
  url: string
): Promise<LectureNote[] | any> => {
  try {
    const accessToken = await getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
    })

    if (response.status !== 200) {
      throw new ApiError(
        response.status,
        `Network response was not ok: ${response.status} ${response.statusText}`
      )
    }

    const data = await response.json()
    return { data, status: response.status }
  } catch (error: any) {
    console.error(error)
    return { data: null, error }
  }
}

import { getToken } from '@/app/utils/cookie/getToken'

export interface LectureCard {
  lecture_id: number
  teacher_name: string
  lecture_title: string
  lecture_detail: string
  session_id: string
  lecture_schedule: string
  lecture_img_url: string
}

interface CreateLectureNoteRequestBody {
  lecture_id: number
  note_title: string
  note_content: string
}

interface LectureNote {
  note_id: number
  note_title: string
  note_content: string
  note_created_at: string
}

interface TeacherSentence {
  sentence_id: number
  sentence_text: string
  registered: boolean
}

export const getLectureList = async (
  url: string
): Promise<{ data: LectureCard[]; status: number } | undefined> => {
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
export const postCreateOpenViduSession = async (url: string) => {
  try {
    const accessToken = getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
    })

    if (!response.ok) {
      console.log(response)
    }
    const data = await response.json()
    return { data: data.sesstion_id, status: response.status }
  } catch (error) {
    console.error('There was a problem with the fetch operation:', error)
  }
}

export const postCreateOpenViduConnection = async (url: string) => {
  try {
    const accessToken = getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
    })

    if (!response.ok) {
      console.log(response)
    }
    const data = await response.json()
    return { data: data.token, status: response.status }
  } catch (error) {
    console.error('There was a problem with the fetch operation:', error)
  }
}

export const postCreateLectureNote = async (
  url: string,
  payload: CreateLectureNoteRequestBody
) => {
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

export const getLectureNoteList = async (
  url: string
): Promise<{ data: LectureNote[]; status: number } | undefined> => {
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

export const deleteLectureNoteList = async (url: string) => {
  try {
    const accessToken = getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'DELETE',
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

export const getTeacherSentenceList = async (
  url: string
): Promise<{ data: TeacherSentence[]; status: number } | undefined> => {
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

export const postTeacherSentence = async (
  url: string,
  payload: { sentence_id: number }
) => {
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

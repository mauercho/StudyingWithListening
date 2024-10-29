import { ApiError } from '@/app/utils/customError'
import { getToken } from '@/app/utils/cookie/getToken'

interface SortOption {
  empty: boolean
  unsorted: boolean
  sorted: boolean
}

export interface CommentContent {
  comment_id: number
  content: string
  created_at: string
  nickname: string
}

export interface CommunityComment {
  content: CommentContent[]
  empty: boolean
  first: boolean
  last: boolean
  number: number
  numberOfElements: number
  pageable: {
    offset: number
    pageNumber: number
    pageSize: number
    paged: boolean
    sort: SortOption
    unpaged: boolean
  }
  size: number
  sort: SortOption
  totalElements: number
  totalPages: number
}

interface Post {
  boardId: number
  title: string
  content: string
  nickname: string
  commentNum: number
  likeCount: number
  viewCount: number
  createdAt: string
  comments: CommunityComment
  boardImages: string[]
}

export const getPost = async (url: string): Promise<any> => {
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

interface PostPostComment {
  comment_content: string
}

export const postPostComment = async (
  boardId: string,
  payload: PostPostComment
) => {
  try {
    const accessToken = getToken()
    const response = await fetch(
      `${process.env.NEXT_PUBLIC_BASE_URL}/boards/${boardId}/comments`,
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`,
        },
        body: JSON.stringify(payload),
      }
    )

    if (!response.ok) {
      if (response.status === 400) {
        throw new ApiError(
          response.status,
          `Network response was not ok: ${response.status} ${response.statusText}`
        )
      }
    }

    const data = await response.json()
    return { data, error: null }
  } catch (error: any) {
    return {
      data: null,
      error: {
        message: error,
        status: error.status,
      },
    }
  }
}

export const postPost = async (url: string, payload: FormData) => {
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
      if (response.status === 400) {
        throw new ApiError(
          response.status,
          `Network response was not ok: ${response.status} ${response.statusText}`
        )
      }
    }

    const data = await response.json()
    return { data, error: null }
  } catch (error: any) {
    return {
      data: null,
      error: {
        message: error,
        status: error.status,
      },
    }
  }
}

export const getPostList = async (url: string) => {
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

export const deletePost = async (url: string) => {
  try {
    const accessToken = await getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'DELETE',
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
    return {
      data,
      status: response.status,
    }
  } catch (error: any) {
    console.error(error)
    throw error
  }
}

export const deletePostComment = async (url: string) => {
  try {
    const accessToken = await getToken()
    const response = await fetch(`${process.env.NEXT_PUBLIC_BASE_URL}${url}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${accessToken}`,
      },
    })

    console.log(response)

    if (!response.ok) {
      throw new ApiError(
        response.status,
        `Network response was not ok: ${response.status} ${response.statusText}`
      )
    }

    const data = await response.json()
    return {
      data,
      status: response.status,
    }
  } catch (error: any) {
    console.error(error)
    throw error
  }
}

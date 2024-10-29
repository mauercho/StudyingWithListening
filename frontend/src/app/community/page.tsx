'use client'

import useSWR from 'swr'
import TabList from '@/app/community/_components/CommunityTabList'
import ListElement from '@/app/community/_components/CommunityListElement'
import SearchBar from '@/app/community/_components/CommunitySearchBar'
import Pagination from '@/app/community/_components/CommunityPostPagination'
import { getPostList } from '../apis/community'
import Link from 'next/link'
import Image from 'next/image'

interface CommunityProps {
  searchParams: {
    page?: string
    tab?: string
    search?: string
  }
}

interface PostList {
  totalElements: number
  totalPages: number
  pageable: {
    pageNumber: number
    pageSize: number
    sort: { sorted: boolean; unsorted: boolean; empty: boolean }
    offset: number
    paged: boolean
    unpaged: boolean
  }
  sort: { sorted: boolean; unsorted: boolean; empty: boolean }
  first: boolean
  last: boolean
  number: number
  numberOfElements: number
  size: number
  content: PostListContent[]
}

interface PostListContent {
  title: string
  content: string
  nickname: string
  hit: number
  thumbnail: string
  board_id: number
  comment_num: number
  created_at: string
}

const fetcher = (url: string) => getPostList(url)

export default function Community({ searchParams }: CommunityProps) {
  const currentPage = searchParams.page || '0'
  const tab = searchParams.tab || 'total-post'
  const search = searchParams.search

  let apiEndpoint = `/boards?page=${currentPage}&size=10`

  if (tab === 'popular-post') {
    apiEndpoint = `/boards/sorted-by-hit?page=${currentPage}&size=10`
  } else if (tab === 'my-post') {
    apiEndpoint = `/boards/my-content?page=${currentPage}&size=10`
  }

  if (search) {
    apiEndpoint = `/boards/search?keyword=${search}&page=${currentPage}&size=10`
  }

  const { data: postList, error } = useSWR<PostList>(apiEndpoint, fetcher)

  if (error) return <div>Failed to load posts</div>

  return (
    <div className="flex flex-col items-center gap-5">
      <h1 className="text-primary-900 text-3xl font-medium">커뮤니티</h1>
      <div className="w-full h-full max-w-[65rem] bg-white rounded-t-[3.75rem] pt-6 pb-5 px-14 min-h-screen">
        <div className="pb-3 mb-5 pt-9 flex justify-between px-8">
          <TabList />
          <div className="flex gap-2">
            <SearchBar />
            <Link href={'/community/write'}>
              <button className="rounded-2xl py-2 px-10 bg-primary-400 text-white text-base">
                글쓰기
              </button>
            </Link>
          </div>
        </div>
        <div>
          {postList && postList?.content?.length > 0 ? (
            postList?.content?.map((post) => (
              <ListElement key={post.board_id} post={post} />
            ))
          ) : (
            <div className="flex flex-col justify-center items-center h-full min-h-56 gap-3">
              <Image
                src="/images/koala-sleep.png"
                width={80}
                height={80}
                className="w-28 h-28"
                alt="koala"
                draggable="false"
              />
              <p className="text-xl">게시글 내용이 없습니다</p>
            </div>
          )}
        </div>
        <div>
          {postList?.totalPages ? (
            <Pagination
              totalPages={postList?.totalPages}
              type={search ? 'search' : 'tab'}
            />
          ) : null}
        </div>
      </div>
    </div>
  )
}

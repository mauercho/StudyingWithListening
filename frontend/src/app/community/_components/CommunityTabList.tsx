'use client'

import { useRouter, useSearchParams } from 'next/navigation'
import { useState } from 'react'

interface Tab {
  id: string
  label: string
}

export type TabId = 'total-post' | 'popular-post' | 'lecture-chat' | 'my-post'

export const tabList: Tab[] = [
  {
    id: 'total-post',
    label: '전체',
  },
  {
    id: 'popular-post',
    label: '인기글',
  },
  {
    id: 'my-post',
    label: '내가 쓴 글',
  },
]

export default function CommunityTabList() {
  const router = useRouter()
  const searchParams = useSearchParams()
  const tabId = searchParams.get('tab') || 'total-post'

  const handleClickTab = (tabId: string) => {
    router.replace(`/community?page=0&tab=${tabId}`)
  }

  return (
    <ul className="inline-flex gap-6 items-end">
      {tabList.map((tab) => {
        return (
          <li
            key={tab.label}
            className={`hover:text-primary-400 cursor-pointer ${tabId === tab.id ? 'text-primary-400 border-b-2' : 'text-gray-400'} text-lg font-medium px-2 border-primary-400`}
            onClick={() => handleClickTab(tab.id)}
          >
            {tab.label}
          </li>
        )
      })}
    </ul>
  )
}

'use client'

import { useEffect, useRef, useState } from 'react'
import KebabMenu from '/public/icons/kebab-menu.svg'
import useSWR from 'swr'
import { getUserInfo } from '@/app/apis/auth'

interface CommunityPostKebabMenuProps {
  size: {
    width: number
    height: number
  }
  onClick: React.MouseEventHandler<HTMLButtonElement>
  nickname: string
}

export default function CommunityPostKebabMenu({
  size: { width, height },
  onClick,
  nickname,
}: CommunityPostKebabMenuProps) {
  const { data: userInfo } = useSWR('/users', getUserInfo)
  const [isVisible, setIsVisible] = useState(false)
  const menuRef = useRef<HTMLDivElement>(null)

  const handleClickReportButton = () => {
    alert('신고 완료!!')
  }

  useEffect(() => {
    function handleClickOutside(e: any) {
      if (menuRef.current && !(menuRef.current as any).contains(e.target)) {
        setIsVisible(false)
      }
    }
    document.addEventListener('mousedown', handleClickOutside)
    return () => {
      document.removeEventListener('mousedown', handleClickOutside)
    }
  }, [])

  return (
    <div className="relative flex items-center" ref={menuRef}>
      <button
        onClick={() => {
          setIsVisible((visible) => !visible)
        }}
      >
        <KebabMenu width={width} height={height} />
      </button>

      {isVisible ? (
        <div className="bg-white flex flex-col border shadow-md rounded-md absolute top-8 z-10 right-0">
          {nickname === userInfo?.data.nickname ? (
            <button
              onClick={onClick}
              className="py-3 text-left pl-4 w-36 text-base text-slate-900 hover:bg-gray-50"
            >
              삭제하기
            </button>
          ) : null}
          <button
            onClick={handleClickReportButton}
            className="text-red-700 py-3 text-left pl-4 w-36 text-base cursor-pointer hover:bg-gray-50"
          >
            신고하기
          </button>
        </div>
      ) : null}
    </div>
  )
}

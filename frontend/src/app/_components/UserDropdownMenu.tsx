'use client'

import Image from 'next/image'
import { useRouter } from 'next/navigation'
import LectureNoteIcon from '/public/icons/notebook.svg'
import ReportIcon from '/public/icons/graph.svg'
import LogoutIcon from '/public/icons/logout.svg'
import EditIcon from '/public/icons/edit-pencil.svg'
import { getUserInfo, postLogout } from '@/app/apis/auth'
import { deleteCookie } from 'cookies-next'
import useSWR, { useSWRConfig } from 'swr'

const USER_DROPDOWN_MENU_LIST = [
  { id: 'lecture-note', label: '강의노트', icon: <LectureNoteIcon /> },
  { id: 'report', label: '학습 리포트', icon: <ReportIcon /> },
]
interface UserDropdownMenuProps {
  setIsUserMenuOpen: React.Dispatch<React.SetStateAction<boolean>>
}

export default function UserDropdownMenu({
  setIsUserMenuOpen,
}: UserDropdownMenuProps) {
  const { data: userInfo } = useSWR('/users', getUserInfo)
  const router = useRouter()
  const { mutate } = useSWRConfig()

  const handleMenuClick = (path: string) => {
    router.push(path)
    setIsUserMenuOpen(false)
  }

  const handleLogout = async () => {
    const res = await postLogout('/users/logout')
    if (res?.status === 200) {
      alert('로그아웃이 완료되었습니다!')
      deleteCookie('accessToken')
      deleteCookie('refreshToken')
      mutate('/users')
      router.push('/')
      setIsUserMenuOpen(false)
    }
  }

  return (
    <div className="p-6 pb-4 w-60 bg-white rounded-3xl text-primary-900 shadow-md">
      <header className="flex gap-3 items-center mb-4 w-full">
        <Image
          src="/images/koala-sleep.png"
          width={100}
          height={100}
          alt="profile"
          className="rounded-full w-12"
        />
        <div>
          <div className="flex items-center gap-2">
            <p>{userInfo?.data.nickname}</p>
            <button onClick={() => router.push('/modals/edit-nickname')}>
              <EditIcon className="w-4" />
            </button>
          </div>
          <p className="text-primary-400 text-sm">
            Lv. {userInfo?.data.user_level}
          </p>
        </div>
      </header>
      <ul className="flex flex-col gap-2">
        {USER_DROPDOWN_MENU_LIST.map((item) => (
          <li key={item.id} className="py-2">
            <button
              onClick={() => handleMenuClick(`/${item.id}`)}
              className="text-gray-700 flex gap-3 hover:text-gray-900 w-full text-left"
            >
              <div className="w-6">{item.icon}</div>
              <p>{item.label}</p>
            </button>
          </li>
        ))}
        <hr />
        <li className="py-2">
          <button
            onClick={handleLogout}
            className="text-gray-700 flex gap-3 hover:text-gray-900 w-full text-left"
          >
            <LogoutIcon className="w-6" />
            <p>로그아웃</p>
          </button>
        </li>
      </ul>
    </div>
  )
}

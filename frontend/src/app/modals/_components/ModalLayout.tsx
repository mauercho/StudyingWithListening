'use client'

import { useRouter } from 'next/navigation'
import CloseBtn from '/public/icons/cancel-btn.svg'

export function ModalLayout({ children }: { children: React.ReactNode }) {
  const router = useRouter()

  return (
    <div
      onClick={() => router.back()}
      className="fixed top-0 w-screen h-screen cursor-pointer bg-[rgba(0,0,0,0.3)] z-[100] flex justify-center items-center"
    >
      <div className="relative" onClick={(e) => e.stopPropagation()}>
        <CloseBtn
          onClick={() => router.back()}
          className="absolute w-4 right-6 top-6 text-gray-900"
        />
        {children}
      </div>
    </div>
  )
}

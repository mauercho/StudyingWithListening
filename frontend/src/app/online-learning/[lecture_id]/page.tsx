'use client'

import { useEffect, useState } from 'react'
import Image from 'next/image'
import OnlineLearningSideBar from '@/app/online-learning/_components/OnlineLearningSideBar'
import OnlineLearningVideoChat from '@/app/online-learning/_components/OnlineLearningVideoChat'
import OnlineLearningLayout from '@/app/online-learning/_components/OnlineLearningLayout'

export default function OnlineLearningRoom() {
  const [isOpenSideBar, setIsOpenSideBar] = useState(false)

  useEffect(() => {
    return () => {
      sessionStorage.clear()
    }
  }, [])

  return (
    <OnlineLearningLayout>
      <div className="flex items-center pr-20 h-full">
        <div
          className={`${isOpenSideBar ? 'w-[calc(100vw-43rem)]' : 'w-[calc(100vw-25rem)] ml-40'}  ml-20 transition-all duration-300 relative`}
        >
          <div className="absolute right-0 z-10 translate-x-10 -translate-y-8">
            <Image
              src="/images/cloud.svg"
              alt="cloud"
              width={140}
              height={140}
              priority
              className="relative"
            />
            <Image
              src="/images/koala-front.png"
              alt="koala-front"
              width={80}
              height={80}
              priority
              className="relative -translate-y-28 translate-x-6"
            />
          </div>
          <OnlineLearningVideoChat />
        </div>
        <OnlineLearningSideBar setIsOpenSideBar={setIsOpenSideBar} />
      </div>
    </OnlineLearningLayout>
  )
}

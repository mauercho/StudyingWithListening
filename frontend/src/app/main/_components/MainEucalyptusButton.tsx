'use client'

import Image from 'next/image'
import { useState } from 'react'
import { motion } from 'framer-motion'
import useSWR, { useSWRConfig } from 'swr'
import { KoalaInfo, feedKoala, fetchKoalaInfo } from '@/app/apis/koala'
import { getUserInfo } from '@/app/apis/auth'

const SCALE_FACTOR = 0.3
const TOP_OFFSET = 20

export default function MainEucalyptusButton() {
  const { mutate } = useSWRConfig()
  const { data: userInfo } = useSWR('/users', getUserInfo)
  const { data: koalaInfo, error } = useSWR<KoalaInfo>(
    '/koalas',
    fetchKoalaInfo
  )

  const [isAnimate, setIsAnimate] = useState(false)
  const [isPressed, setIsPressed] = useState(false)

  const handleClickEucalyptusButton = async () => {
    if (userInfo?.data?.leaves <= 0) return
    setIsAnimate(true)

    try {
      const result = await feedKoala(`/koalas/${koalaInfo?.koala_id}/leaves`)

      if (result?.ok) {
        mutate('/koalas')
        mutate('/users')
      }
    } catch (error) {
      console.error('Failed to update:', error)
    }
  }

  return (
    <div className="inline-block relative w-auto">
      <div className="relative flex justify-center">
        {new Array(5).fill(0).map((_, index) => {
          return (
            <motion.p
              key={`${userInfo?.data?.leaves}`}
              className={`absolute z-10 ${isAnimate ? 'block' : 'hidden'}  pointer-events-none`}
              animate={
                isAnimate
                  ? {
                      top: -50 + index * -TOP_OFFSET,
                      scale: 2 - index * SCALE_FACTOR,
                      left: index % 2 === 0 ? 40 : 'none',
                      right: index % 2 === 1 ? 10 : 'none',
                      opacity: 0,
                    }
                  : {}
              }
              transition={{ duration: 1.5 }}
            >
              <Image
                src="/images/eucalyptus.png"
                width={0}
                height={0}
                sizes="100%"
                className="w-[3rem] h-[3rem]"
                alt="eucalyptus"
                draggable="false"
              />
            </motion.p>
          )
        })}
      </div>
      <button
        className={`bg-[#FFF8DA] w-24 h-24 rounded-full shadow-lg cursor-pointer transition-transform duration-200 ease-in-out ${isPressed ? 'transform scale-95 shadow-inner' : ''}`}
        onClick={handleClickEucalyptusButton}
        onMouseDown={() => {
          if (userInfo?.data?.leaves <= 0) return
          setIsPressed(true)
        }}
        onMouseUp={() => setIsPressed(false)}
        onMouseLeave={() => setIsPressed(false)}
      >
        <Image
          src="/images/eucalyptus.png"
          width={0}
          height={0}
          sizes="100%"
          className="w-full h-full rounded-full"
          alt="eucalyptus"
          draggable="false"
        />
      </button>
      <span className="select-none cursor-pointer absolute -right-2 top-0 bg-[#FF7A7A] text-white text-sm h-8 aspect-square rounded-full flex justify-center items-center font-semibold z-0">
        {userInfo?.data?.leaves >= 100 ? '99+' : userInfo?.data?.leaves}
      </span>
    </div>
  )
}

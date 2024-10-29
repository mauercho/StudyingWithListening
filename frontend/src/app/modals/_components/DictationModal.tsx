'use client'

import Link from 'next/link'
import { useState } from 'react'
import PrevButton from '/public/icons/next-btn.svg'
import Image from 'next/image'

const DICTATION_CATEGORY = [
  {
    id: 'daily',
    label: '일상',
    imageUrl: '/images/koala-default.png',
  },
  {
    id: 'administration',
    label: '행정',
    imageUrl: '/images/koala-business.png',
  },
  {
    id: 'education',
    label: '교육',
    imageUrl: '/images/koala-study.png',
  },
  {
    id: 'custom',
    label: '사용자',
    imageUrl: '/images/koala-listening.png',
  },
]

const DICTATION_MODE = [
  {
    id: 'typing',
    label: '키보드 타이핑',
    imageUrl: '/images/koala-keyboard.png',
  },
  {
    id: 'writing',
    label: '손글씨',
    imageUrl: '/images/koala-pencil.png',
  },
]

export default function DictationCategoryModal() {
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null)

  return (
    <div className="w-auto bg-[#EAEEF2] rounded-3xl p-12">
      {selectedCategory ? (
        <>
          <div className="flex items-center gap-4 mb-4">
            <button
              onClick={() => setSelectedCategory(null)}
              className="text-[#7387D4] scale-x-[-1] inline-block"
            >
              <PrevButton width="30" height="30" />
            </button>
            <p className="text-gray-900 text-lg font-medium">
              받아쓰기 모드를 선택해주세요
            </p>
          </div>

          <div className="grid grid-cols-2 gap-4">
            {DICTATION_MODE.map((mode) => (
              <Link
                href={`/dictation/?category=${selectedCategory}&mode=${mode.id}`}
                key={mode.id}
                className="cursor-pointer transition-all text-xl text-primary-800 duration-300 ease-out hover:bg-[#7387D4] hover:text-white  h-[1/2] aspect-square bg-white flex flex-col justify-center items-center py-16 rounded-3xl"
              >
                <Image
                  src={mode.imageUrl}
                  alt="cloud"
                  width={96}
                  height={0}
                  className="w-24"
                  priority
                />
                <p>{mode.label}</p>
              </Link>
            ))}
          </div>
        </>
      ) : (
        <div className="flex flex-col  gap-8">
          <p className="text-gray-900 text-lg font-medium">
            받아쓰기 주제를 선택해주세요.
          </p>
          <div className="grid grid-cols-2 gap-6">
            {DICTATION_CATEGORY.map((category) => (
              <div
                onClick={() => setSelectedCategory(category.label)}
                key={category.id}
                className="cursor-pointer transition-all text-xl text-primary-800 duration-300 ease-out hover:bg-[#7387D4] hover:text-white  h-[1/2] aspect-square bg-white flex flex-col justify-center items-center py-16 rounded-3xl"
              >
                <Image
                  src={category.imageUrl}
                  alt="cloud"
                  width={64}
                  height={0}
                  className="w-16"
                  priority
                />
                <p>{category.label}</p>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}

'use client'

import React, { useState } from 'react'
import { motion } from 'framer-motion'
import Image from 'next/image'
import Link from 'next/link'
import { useRouter } from 'next/navigation'

const CARD_OFFSET = 100
const SCALE_FACTOR = 0.06
const CARD_LIST = [
  {
    title: '받아쓰기',
    content: '음성을 듣고 쓰기 연습을 해요',
    href: '/modals/dictation-category',
  },
  {
    title: 'AI회화',
    content: '다양한 상황을 AI와 대화해요',
    href: '/ai-speaking',
  },
  {
    title: '화상수업',
    content: '한국인 강사와 함께 한국어를 즐겁게 배워보아요',
    href: '/online-learning',
  },
  {
    title: '복습하기',
    content: '반복 학습으로 실력UP!',
    href: '/review',
  },
]

export default function MainEducationCardList() {
  const router = useRouter()

  const [cardList, setCardList] = useState(CARD_LIST)
  const [startY, setStartY] = useState(0)
  const [endY, setEndY] = useState(0)

  const handleDragStart = (e: DragEvent) => {
    setStartY(e.clientY)
  }

  const handleDrag = (e: DragEvent) => {
    if (e.clientY === 0) return
    setEndY(e.clientY)
  }

  const handleDragEnd = () => {
    const direction = endY > startY ? 'down' : 'up'

    const nCardList = [...cardList]
    if (direction === 'up') {
      const last = nCardList.splice(nCardList.length - 1, 1)
      nCardList.unshift(last[0])
      setCardList(nCardList)
    } else if (direction === 'down') {
      const first = nCardList.splice(0, 1)
      nCardList.push(first[0])
      setCardList(nCardList)
    }

    setStartY(0)
    setEndY(0)
  }

  return (
    <div className="flex justify-end items-center z-10">
      <ul className="absolute -right-16">
        {cardList.map((card, index) => {
          const canDrag = index === 0

          return (
            <motion.li
              onClick={() => router.push(card.href)}
              key={card.title}
              className={`${canDrag ? '' : 'blur-sm'} ${canDrag ? 'cursor-grab' : 'cursor-auto'} bg-primary-400 p-1 box-content rounded-3xl overflow-hidden flex justify-center items-center absolute w-[51rem] h-44 select-none`}
              initial={false}
              animate={{
                top: index !== 3 ? index * -CARD_OFFSET : 100,
                right:
                  index !== 3 ? index * -CARD_OFFSET * 2 : index * -CARD_OFFSET,
                scale: 1 - index * SCALE_FACTOR,
                zIndex: CARD_LIST.length - index,
              }}
              drag={canDrag ? 'y' : false}
              dragConstraints={{
                top: 0,
                bottom: 0,
              }}
              onDragStart={handleDragStart}
              onDrag={handleDrag}
              onDragEnd={() => handleDragEnd()}
              transition={{
                duration: 0.5,
              }}
            >
              <div
                className={`py-11 pl-12 pr-28 flex items-center justify-between border-[0.2rem] border-white w-full h-full rounded-3xl ${index === 0 ? 'bg-primary-400' : 'bg-white'}`}
              >
                <div
                  className={`flex flex-col ${index === 0 ? 'text-white' : 'text-primary-400'} gap-3`}
                >
                  <h3 className="text-3xl font-medium">{card.title}</h3>
                  <p className="text-xl font-normal">{card.content}</p>
                </div>
                {/* <Image
                  src="/images/koala-sleep.png"
                  width={0}
                  height={0}
                  sizes="100%"
                  className="w-36 h-20"
                  alt="card-icon"
                  draggable="false"
                /> */}
              </div>
            </motion.li>
          )
        })}
      </ul>
    </div>
  )
}

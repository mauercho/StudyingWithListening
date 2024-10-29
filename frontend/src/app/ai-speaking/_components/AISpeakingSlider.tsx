'use client'

import { useState } from 'react'
import { motion, PanInfo } from 'framer-motion'
import AISpeakingConversationCard from './AISpeakingConversationCard'
import NextBtn from '/public/icons/arrow-right.svg'
import { useRouter, useSearchParams } from 'next/navigation'
import {
  AIConversationCard,
  getAIConversationList,
} from '@/app/apis/ai-speaking'
import useSWR from 'swr'

const POSITIONS = ['farLeft', 'left', 'center', 'right', 'farRight']

const imageVariants = {
  center: { x: '0%', scale: 1, zIndex: 5, rotate: '0deg' },
  left: {
    x: '-70%',
    scale: 0.85,
    zIndex: 2,
    rotate: '1deg',
    filter: 'blur(2px)',
  },
  farLeft: {
    x: '-130%',
    y: '10%',
    scale: 0.8,
    zIndex: 1,
    rotate: '-10deg',
    filter: 'blur(2px)',
  },
  farRight: {
    x: '130%',
    y: '10%',
    scale: 0.8,
    zIndex: 1,
    rotate: '10deg',
    filter: 'blur(2px)',
  },
  right: {
    x: '70%',
    scale: 0.85,
    zIndex: 2,
    rotate: '-1deg',
    filter: 'blur(2px)',
  },
}

export default function AISpeakingSlider() {
  const searchParams = useSearchParams()
  const topic = searchParams.get('topic') || '전체'

  const { data: cardList } = useSWR(
    topic === '전체'
      ? '/ai-talk/situation'
      : `/ai-talk/situation?topic=${topic}`,
    getAIConversationList
  )

  const [activeIndex, setActiveIndex] = useState(0)
  const router = useRouter()

  const handleClick = (direction: number) => {
    setActiveIndex(
      (prevIndex) =>
        (prevIndex + direction + (cardList?.data.length as number)) %
        (cardList?.data.length as number)
    )
  }

  const handleDragEnd = (
    _: MouseEvent | TouchEvent | PointerEvent,
    info: PanInfo
  ) => {
    if (info.offset.x < -100) {
      handleClick(1)
    } else if (info.offset.x > 100) {
      handleClick(-1)
    }
  }
  const getVisibleCards = () => {
    const totalCards = cardList?.data.length as number
    const visibleCards = []
    for (let i = -2; i <= 2; i++) {
      visibleCards.push(
        cardList?.data[(activeIndex + i + totalCards) % totalCards]
      )
    }
    return visibleCards as AIConversationCard[]
  }

  return (
    <div className="h-main-screen flex flex-col items-center justify-center w-full">
      <div className="z-10 w-[28rem] absolute flex justify-between text-gray-900">
        <NextBtn
          onClick={() => handleClick(-1)}
          className="right-[30%] w-10 rotate-180 cursor-pointer"
        />
        <NextBtn
          onClick={() => handleClick(1)}
          className="left-[30%] w-10 cursor-pointer"
        />
      </div>
      {cardList?.data &&
        getVisibleCards().map((card, idx) => {
          const canDrag = POSITIONS[idx] === 'center'
          return (
            <motion.div
              key={card.situation_id}
              className={`absolute ${canDrag ? 'cursor-pointer' : 'cursor-auto'}`}
              initial="center"
              animate={POSITIONS[idx]}
              variants={imageVariants}
              transition={{ duration: 0.5 }}
              drag={canDrag ? 'x' : false}
              onDragEnd={handleDragEnd}
              dragConstraints={{ left: 0, right: 0 }}
            >
              {
                <div
                  onClick={() => {
                    if (canDrag) {
                      router.push(
                        `ai-speaking/${card.situation_id}?topic=${card.topic_category}`
                      )
                    }
                  }}
                >
                  <AISpeakingConversationCard
                    conversationTitle={card.situation_title}
                    conversationDescription={card.situation_detail}
                    conversationCoverImg={card.situation_img_url}
                  />
                </div>
              }
            </motion.div>
          )
        })}
    </div>
  )
}

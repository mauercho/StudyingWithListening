'use client'

import { useState } from 'react'
import { motion } from 'framer-motion'
import Image from 'next/image'
import LeftButton from '/public/icons/left-circle-button.svg'
import RightButton from '/public/icons/right-circle-button.svg'

interface CommunityCarouselProps {
  boardImageList: string[]
}

export default function CommunityCarousel({
  boardImageList,
}: CommunityCarouselProps) {
  const [currentSlide, setCurrentSlide] = useState(0)

  const nextSlide = () => {
    setCurrentSlide((prevSlide) => (prevSlide + 1) % boardImageList.length)
  }

  const prevSlide = () => {
    setCurrentSlide(
      (prevSlide) =>
        (prevSlide - 1 + boardImageList.length) % boardImageList.length
    )
  }

  return (
    <div className="flex-col w-full flex gap-2 justify-center pt-14 pb-9 overflow-hidden">
      {boardImageList?.length > 0 ? (
        <>
          <div className="">
            <motion.div
              initial={{ x: 200 }}
              animate={{ x: 200 + -currentSlide * 500 }}
              transition={{ stiffness: 0 }}
              className="flex gap-10 w-full"
            >
              {boardImageList.map((src, index) => {
                return (
                  <Image
                    src={src}
                    width={460}
                    height={460}
                    className={`w-[460px] h-[460px] aspect-square border border-gray-200 object-cover rounded-2xl ${index === currentSlide ? '' : 'opacity-30'}`}
                    alt="content_image"
                    key={index}
                  />
                )
              })}
            </motion.div>
            <button
              onClick={prevSlide}
              className="absolute left-[140px] top-1/2 rounded-full"
            >
              <LeftButton />
            </button>
            <button
              onClick={nextSlide}
              className="absolute right-[140px] top-1/2 rounded-full"
            >
              <RightButton />
            </button>
          </div>
          <div className="flex gap-2 justify-center">
            {boardImageList.map((_, index) => {
              return (
                <span
                  key={index}
                  className={` w-3 h-3 flex cursor-pointer rounded-full
              ${currentSlide === index ? 'bg-blue-300' : 'bg-gray-300'}
                `}
                  onClick={() => {
                    setCurrentSlide(index)
                  }}
                ></span>
              )
            })}
          </div>
        </>
      ) : null}
    </div>
  )
}

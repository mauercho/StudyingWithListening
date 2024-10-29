'use client'

import FilterIcon from '/public/icons/filter-square.svg'
import SearchIcon from '/public/icons/search-normal.svg'
import { useState } from 'react'
import { SubmitHandler, useForm } from 'react-hook-form'
import { motion, Variants } from 'framer-motion'
import { useRouter } from 'next/navigation'
import { Category } from '@/app/review/_components/ReviewArea'

const categoryList = [
  { id: '1', content: '전체' },
  { id: '2', content: '일상' },
  { id: '3', content: '행정' },
  { id: '4', content: '교육' },
  { id: '5', content: '사용자' },
]

const itemVariants: Variants = {
  open: {
    opacity: 1,
    y: 0,
    transition: { type: 'spring', stiffness: 300, damping: 24 },
  },
  closed: { opacity: 0, y: 20, transition: { duration: 0.2 } },
}

const ulVariants: Variants = {
  open: {
    clipPath: 'inset(0% 0% 0% 0% round 10px)',
    transition: {
      type: 'spring',
      bounce: 0,
      duration: 0.7,
      delayChildren: 0.3,
      staggerChildren: 0.05,
    },
  },
  closed: {
    clipPath: 'inset(10% 50% 90% 50% round 10px)',
    transition: {
      type: 'spring',
      bounce: 0,
      duration: 0.3,
    },
  },
}

interface SearchProps {
  topic?: string | null
  keyword?: string | null
}

interface KeywordFormValues {
  keyword?: string
}

export default function ReviewAriaSearch({ topic, keyword }: SearchProps) {
  const [isOpen, setIsOpen] = useState(false)
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<KeywordFormValues>()
  const router = useRouter()

  const handleTopic = (e: React.MouseEvent<HTMLLIElement>) => {
    e.preventDefault()
    const targetContent = e.target as HTMLLIElement
    const topicContent = targetContent.textContent || '전체'
    setIsOpen((isOpen) => !isOpen)
    if (topicContent && topicContent !== '전체') {
      if (keyword) {
        router.push(`/review?topic=${topicContent}&keyword=${keyword}`)
      } else {
        router.push(`/review?topic=${topicContent}`)
      }
    } else {
      if (keyword) {
        router.push(`/review?keyword=${keyword}`)
      } else {
        router.replace(`/review`)
      }
    }
  }

  const OnSubmit:SubmitHandler<KeywordFormValues> = (data) => {
    if (data.keyword) {
      if (topic) {
        router.push(`/review?topic=${topic}&keyword=${data.keyword}`)
      } else {
        router.push(`/review?keyword=${data.keyword}`)
      }
    } else {
      if (topic) {
        router.push(`/review?topic=${topic}`)
      } else {
        router.replace('/review')
      }
    }
  }

  const LiElement = ({ id, content }: Category) => (
    <motion.li
      id={id}
      variants={itemVariants}
      className="text-center hover:text-white py-[0.5rem] hover:bg-gray-400"
      onClick={handleTopic}
    >
      <p>{content}</p>
    </motion.li>
  )

  return (
    <>
      <motion.nav
        initial={false}
        animate={isOpen ? 'open' : 'closed'}
        className="menu mr-5"
      >
        <motion.button
          whileTap={{ scale: 0.97 }}
          onClick={() => setIsOpen(!isOpen)}
          className="w-[11rem] h-full bg-primary-400 rounded-[4.5rem] flex items-center pr-5"
        >
          <FilterIcon width={24} height={24} className="ml-[1rem]" />
          <p className="m-auto text-white">{topic ? topic : '전체'}</p>
        </motion.button>
        <motion.ul
          className="bg-white mt-1"
          variants={ulVariants}
          style={{ pointerEvents: isOpen ? 'auto' : 'none' }}
        >
          {categoryList.map((category) => {
            return (
              <LiElement
                key={category.id}
                id={category.id}
                content={category.content}
              />
            )
          })}
        </motion.ul>
      </motion.nav>
      <form
        className="flex w-full h-full rounded-[4.5rem] bg-gray-100"
        onSubmit={handleSubmit(OnSubmit)}
      >
        <SearchIcon width={24} height={24} className="mx-[1rem] my-auto" />
        <input
          type="text"
          className="focus:outline-none bg-gray-100 rounded-r-[4.5rem] pr-[1.5rem] text-xl grow"
          {...register('keyword')}
        />
      </form>
    </>
  )
}

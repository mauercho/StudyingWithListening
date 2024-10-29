'use client'

import Link from 'next/link'
import { useSearchParams } from 'next/navigation'

type CategoryId = 'all' | 'daily' | 'education' | 'administration'

interface Category {
  id: CategoryId
  label: string
}

const CATEGORY_LIST: Category[] = [
  {
    id: 'all',
    label: '전체',
  },
  {
    id: 'daily',
    label: '일상',
  },
  {
    id: 'education',
    label: '교육',
  },
  {
    id: 'administration',
    label: '행정',
  },
]

export default function AISpeakingCategoryList() {
  const searchParams = useSearchParams()
  const topic = searchParams.get('topic') || '전체'
  return (
    <ul className="flex gap-3 mt-8">
      {CATEGORY_LIST.map((category) => (
        <li key={category.id}>
          <Link href={`/ai-speaking/?topic=${category.label}`}>
            <button
              className={`${topic === category.label ? 'bg-primary-400' : 'bg-gray-300'} text-white py-2 px-6 rounded-full`}
            >
              {category.label}
            </button>
          </Link>
        </li>
      ))}
    </ul>
  )
}

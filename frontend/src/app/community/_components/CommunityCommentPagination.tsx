'use client'

import MoveLeftButton from '/public/icons/arrow-left-no-line.svg'
import MoveRightButton from '/public/icons/arrow-right-no-line.svg'

interface CommunityPaginationProps {
  totalPages: number
  onPageChange: any
  selectedCommentPage: number
}

export default function CommunityPagination({
  totalPages = 0,
  onPageChange,
  selectedCommentPage = 1,
}: CommunityPaginationProps) {
  const handleClickPrevBtn = () => {
    if (selectedCommentPage <= 0) return
    onPageChange((pre: number) => pre - 1)
  }

  const handleClickNextBtn = () => {
    if (selectedCommentPage + 1 >= totalPages) return
    onPageChange((pre: number) => pre + 1)
  }

  return (
    <div className="flex gap-7 py-5 mt-3 mb-10 justify-center items-center">
      {selectedCommentPage !== 0 ? (
        <button onClick={handleClickPrevBtn} className="cursor-pointer">
          <MoveLeftButton />
        </button>
      ) : null}

      <div className="flex gap-2">
        {new Array(totalPages).fill(0).map((_, index) => {
          return (
            <button
              onClick={() => onPageChange(index)}
              key={index}
              className={`text-gray-700 text-base font-normal w-7 h-7 ${selectedCommentPage === index ? 'bg-primary-400 text-white rounded-full' : 'bg-none hover:text-primary-400'}`}
            >
              {index + 1}
            </button>
          )
        })}
      </div>
      {selectedCommentPage + 1 !== totalPages ? (
        <button onClick={handleClickNextBtn} className="cursor-pointer">
          <MoveRightButton />
        </button>
      ) : null}
    </div>
  )
}

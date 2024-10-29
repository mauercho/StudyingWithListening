import { getConvertedTime } from '@/app/utils/getConvertedTime'
import Image from 'next/image'

interface LectureNoteBookmarkProps {
  noteId: number
  noteTitle: string
  noteContent: string
  noteCreatedAt: string
}

export default function LectureNoteBookmark({
  noteTitle,
  noteContent,
  noteCreatedAt,
}: LectureNoteBookmarkProps) {
  return (
    <div className="w-full flex flex-col gap-2 ">
      <div className="flex gap-1 items-center px-5">
        <Image
          src="/icons/play.svg"
          width={20}
          height={20}
          alt="play"
          className="w-5 h-5"
        />
        <p className="text-sm text-primary-900 font-normal">
          {getConvertedTime(noteCreatedAt)}
        </p>
      </div>
      <div className="flex flex-col items-start bg-gray-100 rounded-[2rem] py-5 px-7 gap-3 text-gray-900">
        <h3>{noteTitle}</h3>
        <div className="whitespace-pre-line">{noteContent}</div>
      </div>
    </div>
  )
}

'use client'

import { deleteLectureNoteList } from '@/app/apis/online-learning'
import RemoveBtn from '/public/icons/cancel-btn.svg'
import { mutate } from 'swr'
import { useParams } from 'next/navigation'
interface OnlineLearningLectureNoteCardProps {
  noteId: number
  noteTitle: string
  noteContent: string
}

export default function OnlineLearningLectureNoteCard({
  noteId,
  noteTitle,
  noteContent,
}: OnlineLearningLectureNoteCardProps) {
  const params = useParams()
  const { lecture_id } = params
  const handleDeleteLectureNote = async () => {
    const res = await deleteLectureNoteList(`/lectures/note/${noteId}`)
    if (res?.status === 200) {
      mutate(`/lectures/${lecture_id}/note`)
    }
  }

  return (
    <div className="w-full p-5 rounded-3xl bg-primary-400 text-white">
      <div className="flex justify-between">
        <h1 className="text-xl font-medium">{noteTitle}</h1>
        <button onClick={handleDeleteLectureNote}>
          <RemoveBtn width="16" height="16" />
        </button>
      </div>
      <p>{noteContent}</p>
    </div>
  )
}

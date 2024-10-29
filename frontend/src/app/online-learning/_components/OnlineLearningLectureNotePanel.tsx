'use client'
import useSWR from 'swr'
import OnlineLearningLectureNoteCard from './OnlineLearningLectureNoteCard'
import OnlineLearningLectureNoteInput from './OnlineLearningLectureNoteInput'
import { useParams } from 'next/navigation'
import { getLectureNoteList } from '@/app/apis/online-learning'

export default function OnlineLearningLectureNotePanel() {
  const params = useParams()
  const { lecture_id } = params
  const { data: LectureNoteList } = useSWR(
    `/lectures/${lecture_id}/note`,
    getLectureNoteList
  )

  return (
    <div className="h-full flex flex-col gap-2 justify-between">
      <div className="overflow-scroll">
        <div className="flex flex-col gap-3 pr-3">
          {LectureNoteList?.data.map((note) => (
            <OnlineLearningLectureNoteCard
              key={note.note_id}
              noteId={note.note_id}
              noteTitle={note.note_title}
              noteContent={note.note_content}
            />
          ))}
        </div>
      </div>
      <OnlineLearningLectureNoteInput />
    </div>
  )
}

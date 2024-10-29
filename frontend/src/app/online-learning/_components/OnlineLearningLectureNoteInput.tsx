'use client'

import { SubmitHandler, useForm } from 'react-hook-form'
import NoteAddBtn from '/public/icons/simple-edit.svg'
import { postCreateLectureNote } from '@/app/apis/online-learning'
import { useParams } from 'next/navigation'
import { mutate } from 'swr'

interface LectureNoteFormValues {
  title: string
  content: string
}

export default function OnlineLearningLectureNoteInput() {
  const { register, handleSubmit, reset } = useForm<LectureNoteFormValues>()
  const params = useParams()
  const { lecture_id } = params
  const onSubmit: SubmitHandler<LectureNoteFormValues> = async (data) => {
    const payload = {
      lecture_id: Number(lecture_id),
      note_title: data.title,
      note_content: data.content,
    }
    const res = await postCreateLectureNote('/lectures/note', payload)
    if (res?.status === 201) {
      mutate(`/lectures/${lecture_id}/note`)
      reset()
    }
  }

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="w-full h-36 p-5 rounded-3xl bg-gray-100 flex"
    >
      <div className="flex flex-col flex-1">
        <input
          type="text"
          {...register('title')}
          placeholder="제목"
          className="bg-transparent text-xl font-medium outline-none text-gray-900"
        />
        <textarea
          {...register('content')}
          placeholder="강의 내용을 메모해보세요!"
          className="bg-transparent flex-1 w-full outline-none text-gray-900 resize-none"
        />
      </div>
      <button type="submit" className="text-gray-400 w-6 self-end">
        <NoteAddBtn />
      </button>
    </form>
  )
}

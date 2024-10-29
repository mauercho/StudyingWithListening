'use client'

import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import CommunityImageUploader from '@/app/community/_components/CommunityImageUploader'
import { postPost } from '@/app/apis/community'
import { useRouter } from 'next/navigation'
import { mutate } from 'swr'

export interface ImageList {
  preview: string
  file: File
}

export default function CommunityPostEditor() {
  const router = useRouter()
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({ mode: 'onChange' })
  const [imageList, setImageList] = useState<ImageList[]>([])

  const onSubmit = async (data: any) => {
    const formData = new FormData()
    formData.append(
      'board_detail',
      JSON.stringify({
        board_title: data.title,
        board_content: data.content,
      })
    )

    imageList.forEach((file) => {
      formData.append('board_images', file.file, file.file.name)
    })

    const { error } = await postPost('/boards', formData)
    if (error) {
      if (error.status === 400) {
        alert('한글을 사용한 게시글만 등록할 수 있습니다')
      } else {
        alert('오류가 발생했습니다. 다시 시도해주세요')
      }
      return
    }
    mutate('/boards?page=0&size=10')
    router.replace('/community')
  }

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className=" flex flex-col gap-4 w-[65rem]"
    >
      <div className="flex flex-col px-20 py-12 bg-white w-full max-w-[65rem] h-[40rem] rounded-[4rem]">
        <input
          type="text"
          className={`text-2xl pb-3 border-b 'border-gray-300 outline-none`}
          placeholder="제목을 입력하세요"
          {...register('title', { required: true })}
        />

        <textarea
          className="h-full resize-none mt-9 text-gray-900 text-base outline-none"
          placeholder="내용을 입력하세요"
          {...register('content', { required: true })}
        />
      </div>

      <div className="flex items-start justify-between px-11">
        <CommunityImageUploader
          imageList={imageList}
          setImageList={setImageList}
        />
        <button
          type="submit"
          className="bg-primary-400 py-2 px-12 text-white rounded-full whitespace-nowrap"
        >
          저장
        </button>
      </div>
    </form>
  )
}

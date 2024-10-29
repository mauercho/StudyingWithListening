'use client'

import React from 'react'
import { useForm, FormProvider } from 'react-hook-form'
import AutoResizeTextarea from '@/app/_components/AutoResizeTextarea'
import { postPostComment } from '@/app/apis/community'
import { mutate } from 'swr'

export default function CommunityCommentInput({
  postId,
  selectedCommentPage,
}: {
  postId: number
  selectedCommentPage: number
}) {
  const methods = useForm()

  const onSubmit = async (data: any) => {
    const { error } = await postPostComment(String(postId), data)
    if (error) {
      if (error.status === 400) {
        alert('한글을 사용한 댓글만 등록할 수 있습니다')
      } else {
        alert('오류가 발생했습니다. 다시 시도해주세요')
      }
      return
    }
    mutate(`/boards/${postId}/comments?page=${selectedCommentPage}&size=10`)
    methods.reset({
      comment_content: '',
    })
  }

  return (
    <FormProvider {...methods}>
      <form
        onSubmit={methods.handleSubmit(onSubmit)}
        className="p-6 gap-3 w-full flex flex-col border border-gray-400 rounded-2xl overflow-hidden"
      >
        <AutoResizeTextarea
          fieldName={'comment_content'}
          placeholder={'댓글을 입력해주세요'}
          className={'text-base font-normal text-gray-700'}
        />
        <button
          type="submit"
          className="p-0 inline-block self-end text-base text-gray-500 font-normal"
        >
          등록
        </button>
      </form>
    </FormProvider>
  )
}

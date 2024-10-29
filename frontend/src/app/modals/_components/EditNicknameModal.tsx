'use client'

import { SubmitHandler, useForm } from 'react-hook-form'
import { patchEditUser } from '@/app/apis/auth'
import { useRouter } from 'next/navigation'

interface EditNicknameFormValue {
  nickname: string
}

export default function EditNicknameModal() {
  const {
    register,
    handleSubmit,
    setFocus,
    formState: { isValid },
  } = useForm<EditNicknameFormValue>()
  const rounter = useRouter()

  const onSubmit: SubmitHandler<EditNicknameFormValue> = async (data) => {
    const payload = {
      nickname: data.nickname,
    }
    const res = await patchEditUser('/users', payload)
    if (res?.status === 200) {
      alert('수정이 완료되었습니다!')
      rounter.back()
    } else if (res?.status === 400) {
      alert('중복된 닉네임입니다!')
      setFocus('nickname')
    }
  }
  return (
    <div className="w-80 max-h-80 bg-white rounded-3xl flex flex-col justify-between items-center gap-10 px-8 py-10">
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="w-full flex flex-col gap-2 px-4"
      >
        <input
          {...register('nickname', { required: true })}
          type="text"
          placeholder="새 닉네임"
          className="w-full rounded-full border outline-none px-4 py-2 text-sm"
        />
        <button
          type="submit"
          className={`submit-btn mt-6 text-base h-10 ${isValid ? 'bg-primary-400' : 'bg-gray-300'}`}
        >
          수정하기
        </button>
      </form>
    </div>
  )
}

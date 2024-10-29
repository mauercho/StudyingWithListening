'use client'

import { useState } from 'react'
import Pencil from '/public/icons/pencil.svg'
import { useForm } from 'react-hook-form'
import { KoalaInfo, editKoalaName, fetchKoalaInfo } from '@/app/apis/koala'
import useSWR, { useSWRConfig } from 'swr'

interface MainKoalaNameEditorProps {
  koalaName: string
}

export default function MainKoalaNameEditor({
  koalaName = '코알라',
}: MainKoalaNameEditorProps) {
  const { mutate } = useSWRConfig()
  const { data: koalaInfo, error } = useSWR<KoalaInfo>(
    '/koalas',
    fetchKoalaInfo
  )
  const [isEdit, setIsEdit] = useState(false)
  const {
    register,
    handleSubmit,
    formState: { isValid },
  } = useForm({
    defaultValues: {
      koalaName,
    },
  })

  const onSubmit = async (data: any) => {
    if (!isValid) return
    try {
      const result = await editKoalaName(`/koalas/${koalaInfo?.koala_id}`, {
        koala_name: data.koalaName,
      })

      if (result?.ok) {
        mutate('/koalas')
      }
    } catch (error) {
      console.error('Failed to update:', error)
    } finally {
      setIsEdit(false)
    }
  }

  const handleClickKoalaName = () => {
    setIsEdit(!isEdit)
  }

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="absolute top-0">
      {isEdit ? (
        <div className="flex flex-col items-end gap-2">
          <div className="flex gap-4">
            <button onClick={() => setIsEdit(false)}>취소</button>
            <button
              type="submit"
              className="bg-primary-400 text-white py-1 px-2 rounded-md"
            >
              저장
            </button>
          </div>
          <input
            {...register('koalaName', { required: true })}
            type="text"
            className="shadow-md py-2 px-3 outline-none rounded-lg border border-[#E9E9E9]"
          />
        </div>
      ) : (
        <div
          onClick={handleClickKoalaName}
          className="flex gap-2 items-center group cursor-pointer"
        >
          <p className="text-primary-400 text-2xl font-bold">{koalaName}</p>
          <button className=" hidden group-hover:block">
            <Pencil />
          </button>
        </div>
      )}
    </form>
  )
}

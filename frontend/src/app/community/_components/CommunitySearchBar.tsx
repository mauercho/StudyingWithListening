'use client'

import { useRouter, useSearchParams } from 'next/navigation'
import { useForm } from 'react-hook-form'
import Search from '/public/icons/search.svg'

export default function CommunitySearchBar() {
  const router = useRouter()
  const { register, handleSubmit, reset } = useForm()

  const onSubmit = async (data: any) => {
    router.replace(`/community?search=${data.search}&page=0&size=10`)
  }

  return (
    <form
      onSubmit={handleSubmit(onSubmit)}
      className="w-96 items-center overflow-hidden rounded-full inline-flex bg-white border border-gray-400"
    >
      <input
        {...register('search', { required: true })}
        type="text"
        className="w-full py-2 px-5 outline-none "
      />
      <button type="submit" className="pr-5">
        <Search />
      </button>
    </form>
  )
}

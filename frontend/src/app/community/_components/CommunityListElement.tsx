import Image from 'next/image'
import View from '/public/icons/view.svg'
import Comment from '/public/icons/comment.svg'
import Link from 'next/link'
import { getConvertedTime } from '@/app/utils/getConvertedTime'

export default function Element({ post }: any) {
  return (
    <div className="flex gap-6  py-3 px-8 items-center border-b-[1px] border-gray-400">
      <div className="w-full flex flex-col gap-4">
        <Link href={`/community/${post.board_id}`}>
          <p>{post.title}</p>
        </Link>
        <div className="flex justify-between">
          <div className="flex text-gray-500 text-xs font-normal items-center gap-3">
            <div className="flex items-center">
              <Image
                src="/images/eucalyptus.png"
                width={16}
                height={16}
                className="w-4 h-4 rounded-full"
                alt="eucalyptus"
                draggable="false"
              />
              <p>{post.nickname}</p>
            </div>
            <hr className="h-4 w-[1px] bg-gray-500 border-none" />
            <p>{getConvertedTime(post.created_at)}</p>
          </div>
          <div className="inline-flex gap-6 ">
            <div className="inline-flex items-center gap-1 text-gray-500">
              <View width={16} height={16} />
              <p className="text-xs font-normal">{post.hit}</p>
            </div>
            <div className="inline-flex items-center gap-1 text-gray-500">
              <Comment width={16} height={16} />
              <p className="text-xs font-normal">{post.comment_num}</p>
            </div>
          </div>
        </div>
      </div>
      <Image
        src={post.thumbnail ? post.thumbnail : '/images/eucalyptus.png'}
        width={80}
        height={80}
        className="w-20 h-20 rounded-2xl border aspect-square border-gray-200 object-cover"
        alt="eucalyptus"
        draggable="false"
        priority
      />
    </div>
  )
}

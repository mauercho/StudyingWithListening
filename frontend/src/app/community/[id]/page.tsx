import BackBtn from '/public/icons/next-btn.svg'
import View from '/public/icons/view.svg'
import Comment from '/public/icons/comment.svg'
import CommunityLayout from '@/app/community/_components/CommunityLayout'
import CommunityCarousel from '@/app/community/_components/CommunityCarousel'
import CommnunityComment from '@/app/community/_components/CommunityComment'
import CommunityActionMenu from '@/app/community/_components/CommunityActionMenu'
import { getConvertedTime } from '@/app/utils/getConvertedTime'
import { getPost } from '@/app/apis/community'
import Link from 'next/link'
import Image from 'next/image'

interface CommunityDetailProps {
  params: {
    id: string
  }
}

export default async function CommunityDetail({
  params,
}: CommunityDetailProps) {
  const { id } = params
  const post = await getPost(`/boards/${id}/comments?page=0&size=10`)

  return (
    <CommunityLayout>
      {post && (
        <div className="mt-32 mb-16">
          <div className="flex justify-end px-8 mb-3">
            <Link href="/community">
              <button className="bg-gray-500 text-white py-2 px-12 rounded-full">
                목록
              </button>
            </Link>
          </div>

          <div className="pt-16 pb-14 bg-white max-w-[65rem] min-w-[65rem]  w-full px-11 rounded-[3.75rem] mb-5 relative">
            <button className="absolute top-7 left-7 scale-x-[-1]">
              <BackBtn fill={'#77a3ef'} />
            </button>
            <div className=" flex flex-col px-12 border-b-2 border-gray-300">
              <h3 className="text-2xl font-normal py-3 whitespace-nowrap">
                {post.title}
              </h3>
              <div className="flex justify-between py-5 bg-white whitespace-nowrap gap-5">
                <div className="flex items-center gap-1">
                  <Image
                    src="/images/eucalyptus.png"
                    width={16}
                    height={16}
                    className="w-6 h-6 rounded-full"
                    alt="eucalyptus"
                    draggable="false"
                  />
                  <p className="text-base text-gray-700">{post.nickname}</p>
                </div>
                <div className="flex gap-6 items-center">
                  <div className="flex items-center gap-1">
                    <View width={16} height={16} />
                    <p className="text-gray-700 text-xs">
                      조회 {post.view_count}
                    </p>
                  </div>
                  <div className="flex items-center gap-1">
                    <Comment width={16} height={16} />
                    <p className="text-gray-700 text-xs">
                      댓글 {post.comment_num}
                    </p>
                  </div>
                  <p className="text-gray-700 text-xs">
                    {getConvertedTime(post.created_at)}
                  </p>
                  <CommunityActionMenu
                    nickname={post.nickname}
                    postId={post.board_id}
                  />
                </div>
              </div>
            </div>

            <div className="px-12 relative mb-24">
              <CommunityCarousel boardImageList={post.board_images} />
              <p>{post.content} </p>
            </div>

            <CommnunityComment
              commentList={post.comments}
              postId={post.board_id}
            />
          </div>

          <div className="flex justify-between px-8">
            <Link href="/community/write">
              <button className="bg-primary-400 text-white py-2 px-10 rounded-full">
                글쓰기
              </button>
            </Link>
            <Link href="/community">
              <button className="bg-gray-500 text-white py-2 px-10 rounded-full">
                목록
              </button>
            </Link>
          </div>
        </div>
      )}
    </CommunityLayout>
  )
}

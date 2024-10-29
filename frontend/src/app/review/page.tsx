import ReviewArea from '@/app/review/_components/ReviewArea'
import ReviewLayout from '@/app/review/_components/ReviewLayout'
import { getReviewSentence } from '@/app/apis/review'

interface ReviewProps {
  searchParams: {
    keyword?: string
    topic?: '일상' | '행정' | '교육' | '사용자'
  }
}

export default async function ReviewMain({ searchParams }: ReviewProps) {
  const topic = searchParams?.topic || null
  const keyword = searchParams?.keyword || null
  let url = ''

  if (topic && keyword) {
    url = `/reviews?keyword=${keyword}&topic=${topic}`
  } else if (topic) {
    url = `/reviews?topic=${topic}`
  } else if (keyword) {
    url = `/reviews?keyword=${keyword}`
  } else {
    url = '/reviews'
  }

  const sentenceList = await getReviewSentence(url)
  
  return (
    <ReviewLayout>
      <div className="h-main-screen flex flex-col gap-4">
        <div className="w-[80%] h-full flex max-w-7xl gap-8 mx-auto">
          <ReviewArea topic={topic} keyword={keyword} sentenceList={sentenceList} url={url}/>
        </div>
      </div>
    </ReviewLayout>
  )
} 

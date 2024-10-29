import SentenceAddBtn from '/public/icons/plus-circle.svg'
import { useParams } from 'next/navigation'
import { mutate } from 'swr'
import { postUserSentence } from '../apis/sentence'
import { postTeacherSentence } from '../apis/online-learning'

interface SentenceAddCardProps {
  sentence: string
  sentenceId: number
  handleDeleteuserSentence?: () => void
}

export default function SentenceAddCard({
  sentence,
  sentenceId,
  handleDeleteuserSentence,
}: SentenceAddCardProps) {
  const params = useParams()
  const { lecture_id } = params

  const handleAddUserSentence = async () => {
    const sentenseRes = await postUserSentence('/sentences', {
      sentence_text: sentence,
    })

    if (sentenseRes?.status === 201) {
      if (handleDeleteuserSentence) handleDeleteuserSentence()
    }
  }

  const handleAddTeacherSentence = async () => {
    const reviewsRes = await postTeacherSentence('/reviews', {
      sentence_id: sentenceId,
    })

    if (reviewsRes?.status === 201) {
      mutate(`/lectures/${lecture_id}/sentences`)
    }
  }

  return (
    <div className="w-full min-h-12 rounded-[1.5rem] bg-primary-400 text-white flex justify-between items-center px-4 py-2">
      <p>{sentence}</p>
      <div>
        <SentenceAddBtn
          onClick={
            handleDeleteuserSentence
              ? handleAddUserSentence
              : handleAddTeacherSentence
          }
          className="w-8 cursor-pointer"
        />
      </div>
    </div>
  )
}

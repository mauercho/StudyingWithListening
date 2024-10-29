import SentenceAddCard from '@/app/_components/SentenceAddCard'
import { getTeacherSentenceList } from '@/app/apis/online-learning'
import { useParams } from 'next/navigation'
import useSWR from 'swr'

export default function OnlineLearningHandoutPanel() {
  const params = useParams()
  const { lecture_id } = params
  const { data: teacherSentenceList } = useSWR(
    `/lectures/${lecture_id}/sentences`,
    getTeacherSentenceList
  )
  return (
    <div className="h-full overflow-auto">
      <div className="flex flex-col gap-2 pr-3">
        {teacherSentenceList?.data.map(
          (sentence) =>
            !sentence.registered && (
              <SentenceAddCard
                key={sentence.sentence_id}
                sentenceId={sentence.sentence_id}
                sentence={sentence.sentence_text}
              />
            )
        )}
      </div>
    </div>
  )
}

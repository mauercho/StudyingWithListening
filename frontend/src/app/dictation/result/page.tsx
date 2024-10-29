'use client'

import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import DictationResultLayout from '@/app/dictation/_components/DictationResultLayout'
import GradingSheet from '@/app/dictation/_components/DictationGradingSheet'
import Replay from '/public/icons/re-button.svg'
import { postDictationGrading } from '@/app/apis/dictation'
import { useDictationResult } from '@/app/context/toggleContext'

export default function DictationResult() {
  const router = useRouter()
  const { dictationResult } = useDictationResult()

  const [dictationGradingResultData, setDictationGradingResultData] =
    useState(null)

  useEffect(() => {
    const storedData = localStorage.getItem('dictationGradingResultData')
    const parsedData = storedData ? JSON.parse(storedData) : null
    setDictationGradingResultData(parsedData)
  }, [])

  const gradingDictation = async () => {
    const gradingData = await postDictationGrading(
      '/sentences/writing-test',
      dictationResult
    )

    if (gradingData.status === 200) {
      if (gradingData && gradingData?.data) {
        const { leaves, sentence_test_responses } = gradingData.data

        localStorage.setItem(
          'dictationGradingResultData',
          JSON.stringify(sentence_test_responses)
        )
        setDictationGradingResultData(sentence_test_responses)
      }
    }
  }

  const handleClickReplayButton = () => {
    localStorage.removeItem('dictationGradingResultData')
    setDictationGradingResultData(null)
  }

  const handleClickFinishButton = () => {
    localStorage.removeItem('dictationGradingResultData')
    router.replace('/main')
  }

  useEffect(() => {
    gradingDictation()

    return () => {
      // 컴포넌트가 언마운트될 때 localStorage를 정리
      localStorage.removeItem('dictationGradingResultData')
    }
  }, [])

  return (
    <DictationResultLayout>
      <div className="gap-20 pt-44 pb-40 w-full h-full min-h-main-screen flex items-center flex-col">
        {dictationGradingResultData ? (
          <GradingSheet resultList={dictationGradingResultData} />
        ) : null}
        <div className="flex flex-col gap-12 items-center">
          <button
            onClick={handleClickReplayButton}
            className="px-48 text-2xl font-medium text-white gap-2 items-center rounded-full py-4 bg-primary-400 flex"
          >
            <Replay className="w-8" />
            <p className="leading-10">다시하기</p>
          </button>
          <button
            onClick={handleClickFinishButton}
            className="text-gray-500 font-normal text-2xl py-2"
          >
            나가기
          </button>
        </div>
      </div>
    </DictationResultLayout>
  )
}

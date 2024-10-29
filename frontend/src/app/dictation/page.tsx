'use client'

import React, { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation'
import DictationQuestion from '@/app/dictation/_components/DictationQuestion'
import DictationField from '@/app/dictation/_components/DictationField'
import DictationLayout from '@/app/dictation/_components/DictationLayout'
import DictationFieldMobile from './_components/DictationFieldMobile'
import { useSentences } from '../context/SentenceContext'
import { getDictationSentences } from '../apis/dictation'
import NextButton from '/public/icons/next-btn.svg'
import { useDictationResult } from '../context/toggleContext'

export default function Dictation() {
  const router = useRouter()
  const [mode, setMode] = useState('typing')
  const [questionNum, setQuestionNum] = useState(0)
  const { sentences, updateSentences } = useSentences()
  const [isToggled, setIsToggled] = useState(false)
  const [toggleFlag, setToggleFlag] = useState(false)
  const { updateDictationResultAtIndex } = useDictationResult()
  const [userAnswer, setUserAnswer] = useState('')

  const handleClickNextButton = () => {
    updateDictationResultAtIndex(questionNum, {
      toggled: toggleFlag,
      sentence_id: sentences[questionNum].sentence_id,
      user_sentence: userAnswer,
    })
    if (questionNum == 9) {
      router.push(`dictation/result`)
    } else {
      setQuestionNum((prevNum) => prevNum + 1)
      setIsToggled(false)
      setToggleFlag(false)
      setUserAnswer('')
    }
  }

  useEffect(() => {
    const queryParams = new URLSearchParams(window.location.search)
    const categoryParam = queryParams.get('category')
    const modeParam = queryParams.get('mode')

    setMode(modeParam || 'typing')

    const getDictationSentenceList = async () => {
      if (categoryParam) {
        const dictationSentences = await getDictationSentences(
          `/sentences?topic=${categoryParam}`
        )

        localStorage.setItem('question', JSON.stringify(dictationSentences))
        updateSentences(dictationSentences)
      }
    }

    getDictationSentenceList()
  }, [router])

  useEffect(() => {
    if (isToggled) {
      setToggleFlag(true)
    }
  }, [isToggled])

  useEffect(() => {}, [])

  return (
    <DictationLayout>
      <div className="flex flex-col h-main-screen justify-center gap-3">
        <div className="pt-40 flex items-center flex-col gap-28">
          {sentences && sentences.length > 0 ? (
            <DictationQuestion
              questionIdx={questionNum + 1}
              question={sentences[questionNum]}
              toggle={{ isToggled, setIsToggled }}
            />
          ) : null}
          {mode === 'typing' ? (
            <DictationField userAnswer={{ userAnswer, setUserAnswer }} />
          ) : (
            <DictationFieldMobile userAnswer={{ userAnswer, setUserAnswer }} />
          )}
        </div>
        <button
          onClick={handleClickNextButton}
          className="self-end gap-3 cursor-pointer rounded-full text-primary-400"
        >
          <NextButton width="70" height="70" />
        </button>
      </div>
    </DictationLayout>
  )
}

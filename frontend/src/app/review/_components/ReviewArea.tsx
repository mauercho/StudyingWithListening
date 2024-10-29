'use client'
import ReviewAreaSearch from '@/app/review/_components/ReviewAreaSearch'
import ReviewAreaSentence from '@/app/review/_components/ReviewAreaSetence'
import ReviewMenuButton from '@/app/review/_components/ReviewMenuButton'
import { SentenceContent, deleteReviewSentence } from '@/app/apis/review'
import { getGoogleSpeech } from '@/app/apis/ttsSententce'
import { useState, useEffect, useRef } from 'react'
import { useRouter } from 'next/navigation'

interface ReviewContentProps {
  topic?: string | null
  keyword?: string | null
  sentenceList?: SentenceContent[]
  url: string
}

interface dictSentence {
  sentence_id: number
  sentence_text: string
  sentence_length: number
}

export interface Category {
  id: string
  content: string
}

export const categoryList = [
  { id: '1', content: '전체' },
  { id: '2', content: '일상' },
  { id: '3', content: '행정' },
  { id: '4', content: '교육' },
  { id: '5', content: '사용자' },
]

export default function ReviewArea({
  topic,
  keyword,
  sentenceList,
  url,
}: ReviewContentProps) {
  const [selectedSentences, setSelectedSentences] = useState<number[]>([])
  const [nowPlaying, setNowPlayingSentence] = useState<number>()
  const router = useRouter()
  const audioSourceRef = useRef<AudioBufferSourceNode | null>(null)
  const audioContextRef = useRef<AudioContext | null>(null)
  const [isPlaying, setIsPlaying] = useState<boolean>(false)
  let copySentences: number[] = []
  
  useEffect(() => {
    window.speechSynthesis.getVoices()
    return () => {
      if (audioSourceRef.current) {
        audioSourceRef.current.stop()
      }
      if (audioContextRef.current) {
        audioContextRef.current.close()
      }

      setIsPlaying(false)
    }
  }, [])


  const handleSentenceList = (review_sentence_id: number) => {
    copySentences = [...selectedSentences]
    if (selectedSentences.includes(review_sentence_id)) {
      const idx = copySentences.indexOf(review_sentence_id)
      copySentences.splice(idx, 1)
    } else {
      copySentences.push(review_sentence_id)
    }
    setSelectedSentences((selectedSentences) => copySentences)
  }

  const handleSetenceDelete = async () => {
    if (selectedSentences.length) {
      try {
        await Promise.all(
          selectedSentences.map((review_sentence_id) => {
            deleteReviewSentence('/reviews', review_sentence_id)
          })
        )
        setSelectedSentences([])
        alert('문장을 삭제하였습니다.')
        router.refresh()
      } catch (error) {
        console.error(error)
      }
    }
  }

  const playSentence = async (sentence: SentenceContent) => {
    if (isPlaying) {
      if (audioSourceRef.current) {
        audioSourceRef.current.stop()
        setIsPlaying(false)
      }
    }

    if (audioContextRef.current === null) {
      audioContextRef.current = new window.AudioContext()
    }

    const arraybuff = await getGoogleSpeech(sentence.sentence_text)
    const audiobuff = await audioContextRef.current.decodeAudioData(arraybuff)
    const source = audioContextRef.current.createBufferSource()
    source.buffer = audiobuff
    source.connect(audioContextRef.current.destination)
    
    audioSourceRef.current = source
    setNowPlayingSentence(sentence.review_sentence_id)

    source.start()
    source.onended = () => {
      setNowPlayingSentence(undefined)
      playNextSentence()
    }
  }

  const playNextSentence = () => {
    const nextSentenceId = copySentences.shift()
    if (nextSentenceId !== undefined) {
      const nextSentence = sentenceList?.find(
        (sentence) => sentence.review_sentence_id === nextSentenceId
      )
      if (nextSentence) {
        playSentence(nextSentence)
      }
    }
  }

  const handleSentencePlay = () => {
    if (selectedSentences.length) {
      copySentences = [...selectedSentences]
      playNextSentence()
    } else {
      alert('문장을 선택해주세요.')
    }
  }

  const handleSentenceDictation = () => {
    if (selectedSentences.length >= 10) {
      const dictationData: dictSentence[] = []
      let sentenceData: dictSentence

      copySentences = [...selectedSentences]
      copySentences.sort(() => Math.random() - 0.5)
      copySentences = copySentences.splice(0, 10)
      copySentences.map((review_sentence_id) => {
        sentenceList?.forEach((sentence) => {
          if (sentence.review_sentence_id === review_sentence_id) {
            sentenceData = {
              sentence_id: sentence.sentence_id,
              sentence_text: sentence.sentence_text,
              sentence_length: sentence.sentence_text.length,
            }
            dictationData.push(sentenceData)
          }
        })
      })
      localStorage.setItem('dictationData', JSON.stringify(dictationData))
      router.push('/dictation')
    } else {
      alert('문장을 10개 이상 선택해주세요!')
    }
  }

  const handleSentenceSpeaking = () => {
    alert('서비스 준비 중입니다.')
  }

  return (
    <>
      <div className="h-full flex flex-col bg-white w-full rounded-t-3xl p-6 gap-4">
        <div className="flex w-full h-12">
          <ReviewAreaSearch topic={topic} keyword={keyword} />
        </div>
        <hr className="bg-gray-200" />
        <div className="w-full pr-4 overflow-auto flex flex-col gap-2">
          {sentenceList !== undefined && sentenceList.length ? (
            sentenceList?.map((sentence: SentenceContent) => {
              return (
                <ReviewAreaSentence
                  key={sentence.review_sentence_id}
                  sentence={sentence}
                  isSelected={selectedSentences.includes(
                    sentence.review_sentence_id
                  )}
                  isNowPlaying={sentence.review_sentence_id === nowPlaying}
                  OnSentenceSelect={handleSentenceList}
                />
              )
            })
          ) : (
            <p className="text-center">아직 문장을 추가하지 않았어요!</p>
          )}
        </div>
      </div>
      <div className="flex flex-col self-end mb-4">
        <ReviewMenuButton onClick={handleSentencePlay} content="재생" />
        <ReviewMenuButton
          onClick={handleSentenceSpeaking}
          content="읽고 따라 말하기"
        />
        <ReviewMenuButton
          onClick={handleSentenceDictation}
          content="받아쓰기"
        />
        <ReviewMenuButton onClick={handleSetenceDelete} content="삭제하기" />
      </div>
    </>
  )
}

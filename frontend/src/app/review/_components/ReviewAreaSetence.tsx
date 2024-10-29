'use client'

import Play from '/public/icons/play.svg'
import Pause from '/public/icons/pause.svg'
import { useState, useRef } from 'react'
import { SentenceContent } from '@/app/apis/review'
import { getGoogleSpeech } from '@/app/apis/ttsSententce'

interface SentenceProps {
  sentence: SentenceContent
  isSelected: boolean
  isNowPlaying: boolean
  OnSentenceSelect: (sentence_id: number) => void
}

export default function ReviewAreaSentence({
  sentence,
  isSelected,
  isNowPlaying,
  OnSentenceSelect,
}: SentenceProps) {
  const [isPlaying, setPlaying] = useState<Boolean>(false)
  const audioSourceRef = useRef<AudioBufferSourceNode | null>(null)
  const audioContextRef = useRef<AudioContext | null>(null)

  const handleChangeSelected = () => {
    OnSentenceSelect(sentence.review_sentence_id)
  }

  const handlePlaying = async () => {
    if (isPlaying) {
      if (audioSourceRef.current) {
        audioSourceRef.current.stop()
        setPlaying(false)
      }
    } else {
      if (audioContextRef.current === null) {
        audioContextRef.current = new window.AudioContext()
      }
      const arraybuff = await getGoogleSpeech(sentence.sentence_text)
      const audiobuff = await audioContextRef.current.decodeAudioData(arraybuff)
      const source = audioContextRef.current.createBufferSource()
      source.buffer = audiobuff
      source.connect(audioContextRef.current.destination)
      source.start()
      source.onended = () => setPlaying(false)
      audioSourceRef.current = source
      setPlaying(true)
    }
  }

  return (
    <div
      className={`flex items-center w-full rounded-full px-5 ${isSelected ? 'bg-primary-400' : 'bg-gray-200'}`}
    >
      <p
        onClick={handleChangeSelected}
        className={`h-full grow py-4 ${isSelected ? 'text-white' : ''}`}
      >
        {sentence.sentence_text}
      </p>
      <button onClick={handlePlaying}>
        {isPlaying || isNowPlaying ? (
          <Pause fill={`${isSelected ? 'white' : ''}`} />
        ) : (
          <Play fill={`${isSelected ? 'white' : ''}`} />
        )}
      </button>
    </div>
  )
}

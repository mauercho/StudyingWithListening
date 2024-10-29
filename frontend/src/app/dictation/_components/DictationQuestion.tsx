'use client'

import Image from 'next/image'
import Notification from '/public/icons/notification.svg'
import Repeat from '/public/icons/rotate.svg'
import SoundWave from '@/app/dictation/_components/DictationSoundWave'
import { getGoogleSpeech } from '@/app/apis/ttsSententce'
import { useState } from 'react'

interface DictationQuestionProps {
  questionIdx: number
  question: {
    sentence_id: number
    sentence_text: string
    sentence_length: number
  }
  toggle: {
    isToggled: boolean
    setIsToggled: (type: boolean) => void
  }
}

export default function DictationQuestion({
  questionIdx,
  question,
  toggle: { isToggled, setIsToggled },
}: DictationQuestionProps) {
  const handleChangeToggle = (event: React.ChangeEvent<HTMLInputElement>) => {
    setIsToggled(event.target.checked)
  }

  const [isPlaying, setPlaying] = useState<Boolean>(false)
  let source: AudioBufferSourceNode

  const handlePlaying = async () => {
    const audioContext = new window.AudioContext()
    const arraybuff = await getGoogleSpeech(question.sentence_text)
    const audiobuff = await audioContext.decodeAudioData(arraybuff)
    source = await audioContext.createBufferSource()
    source.buffer = audiobuff
    await source.connect(audioContext.destination)
    source.start()
    await setPlaying(true)
    source.onended = () => setPlaying(false)
  }

  return (
    <div className="w-full max-w-[800px] inline-flex flex-col gap-3 relative">
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="w-40 absolute left-0 top-2 transform -translate-x-28"
        alt="cloud"
        src="/images/cloud.svg"
        draggable="false"
      />

      <Image
        width={0}
        height={0}
        sizes="100%"
        className="w-48 absolute right-0 bottom-0 z-10 transform translate-x-20 translate-y-14"
        alt="cloud"
        src="/images/cloud.svg"
        draggable="false"
      />
      <div className="flex justify-between mx-16">
        <span className="bg-primary-400 py-2 px-8 text-white rounded-full">
          {`${questionIdx}번 문제`}
        </span>
        <div className="z-10 group">
          <div className="text-primary-400 shadow-md absolute -top-20 whitespace-nowrap right-0 bg-white z-10 py-2 px-5 rounded-full opacity-0 group-hover:opacity-100  transition-opacity duration-300">
            <p className="text-gray-700">
              토글 활성화 후 문제를 볼 수 있습니다
            </p>
            <p className="font-medium">
              ※주의사항: 토글을 활성화하면 얻을 수 있는 점수가 낮아집니다
            </p>
          </div>
          <label className="flex cursor-pointer w-auto self-end rounded-full z-10">
            <input
              type="checkbox"
              className="sr-only peer"
              checked={isToggled}
              onChange={handleChangeToggle}
            />
            <div
              className="inline-block self-end w-[90px] h-[42px] border-[2px] border-gray-300 relative bg-white peer-focus:outline-none 
          rounded-full peer peer-checked:after:translate-x-12
          rtl:peer-checked:after:-translate-x-full
          peer-checked:after:border-white 
          after:content-[''] after:absolute after:top-[3px] after:start-[3px]
          after:bg-gray-300 after:rounded-full 
          after:h-8 after:w-8 after:transition-all
          peer-checked:after:bg-primary-400
          peer-checked:border-primary-400 peer-checked:border-[2px]
          peer-checked:bg-white"
            ></div>
          </label>
        </div>
      </div>
      <div className="relative">
        <p className="absolute bottom-[80px] right-[50px]">
          <Image
            width={0}
            height={0}
            sizes="100%"
            className="w-[30rem] h-[30rem]"
            alt="rocket"
            src="/images/rocket.svg"
            draggable="false"
          />
        </p>
        <div className="h-full min-h-[160px] w-full inline-flex gap-5 items-center border-primary-400 border-[4px] rounded-full bg-white pl-[64px] pr-[51px]">
          <p>
            <Notification width="60" height="60" />
          </p>
          {isToggled ? (
            <p className="text-primary-700 text-3xl font-medium w-full">
              {question.sentence_text}
            </p>
          ) : (
            <p className="m-auto">
              <SoundWave />
            </p>
          )}
          <button
            onClick={handlePlaying}
            className="cursor-pointer z-10 text-primary-400"
          >
            <Repeat />
          </button>
        </div>
      </div>
    </div>
  )
}

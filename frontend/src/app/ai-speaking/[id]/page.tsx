'use client'

import ChatBubble from '@/app/_components/ChatBubble'
import MicBtn from '/public/icons/microphone.svg'
import Image from 'next/image'
import { useEffect, useRef, useState } from 'react'
import AISpeakingBackgroundLayout from '@/app/ai-speaking/_components/AISpeakingBackgroundLayout'
import { useParams, useRouter, useSearchParams } from 'next/navigation'
import useSWR from 'swr'
import {
  getEndAISpeaking,
  getStartAISpeaking,
  postSendAISpeakingMessage,
} from '@/app/apis/ai-speaking'
import { playTextToSpeech } from '@/app/utils/playTextToSpeech'
import { useSpeechRecognition } from '@/app/utils/useSpeechRecognition'
import LoadingDots from '@/app/_components/LoadingDots'

type TopicKey = '일상' | '행정' | '교육'

const BACKGROUND_DATA: Record<TopicKey, { bgImage: string; avatar: string }> = {
  일상: {
    bgImage: '/images/daily-background.jpg',
    avatar: '/images/koala-front.png',
  },
  행정: {
    bgImage: '/images/administration-background.jpg',
    avatar: '/images/koala-business.png',
  },
  교육: {
    bgImage: '/images/education-background.jpg',
    avatar: '/images/koala-study.png',
  },
}

interface AISpeakingMessage {
  sender: string
  message: string
  isMine: boolean
}

export default function AISpeakingLearningRoom() {
  const [messages, setMessages] = useState<AISpeakingMessage[]>([])
  const { isRecording, transcription, startRecording, stopRecording } =
    useSpeechRecognition()
  const bottomRef = useRef<HTMLDivElement>(null)
  const router = useRouter()
  const searchParams = useSearchParams()
  const topic = searchParams.get('topic') as TopicKey
  const params = useParams()
  const { id } = params
  const { data: startData } = useSWR(
    `/ai-talk/start?situation=${id}`,
    getStartAISpeaking
  )

  useEffect(() => {
    if (startData?.data && messages.length === 0) {
      setMessages([
        {
          sender: startData.data.ai_role,
          message: startData.data.message,
          isMine: false,
        },
      ])
      playTextToSpeech(startData.data.message)
    }
  }, [startData])

  useEffect(() => {
    if (transcription) {
      setMessages((prevMessages) => [
        ...prevMessages,
        {
          sender: 'user',
          message: transcription,
          isMine: true,
        },
      ])

      const sendMessage = async () => {
        const payload = {
          situation_id: Number(id),
          message: transcription,
        }
        const res = await postSendAISpeakingMessage('/ai-talk', payload)
        if (res?.status === 200) {
          setMessages((prevMessages) => [
            ...prevMessages,
            {
              sender: res.data.ai_role,
              message: res.data.message,
              isMine: false,
            },
          ])
          playTextToSpeech(res.data.message)
        }
      }

      sendMessage()
    }
  }, [transcription])

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' })
  }, [messages])

  const handleExitConversation = async () => {
    stopRecording()
    const res = await getEndAISpeaking('/ai-talk/finish')
    if (res?.status === 200) {
      //TODO: router 모달로 유칼립투스 같이 보내기
      alert(`유칼립투스${res.data.leaves}`)
      router.push('/main')
    }
  }

  return (
    <AISpeakingBackgroundLayout>
      <div className="h-main-screen flex flex-col justify-evenly items-center">
        <div className="h-[80%] w-[80%] max-w-7xl bg-white rounded-3xl mx-auto flex gap-10 p-8">
          <div className="relative bg-gray-300 rounded-tr-3xl overflow-hidden w-[50%] ">
            <Image
              src={BACKGROUND_DATA[topic].bgImage}
              width={500}
              height={500}
              alt="background"
              className="w-full h-full object-cover blur-sm"
              priority
            />
            <Image
              src={BACKGROUND_DATA[topic].avatar}
              alt="koala-front"
              width={300}
              height={300}
              className="absolute bottom-12 left-36"
            />
          </div>
          <div className="w-[50%] flex flex-col gap-12 items-center justify-between">
            <div className="w-full overflow-auto pr-3 flex-1 flex flex-col gap-4">
              {messages.map((chat, idx) => (
                <ChatBubble
                  key={idx}
                  isMine={chat.isMine}
                  message={chat.message}
                  senderName={chat.sender}
                  senderProfile={BACKGROUND_DATA[topic].avatar}
                />
              ))}
              {isRecording && (
                <ChatBubble isMine={true} message={<LoadingDots />} />
              )}
              <div ref={bottomRef} />
            </div>
            <div className="relative w-16 h-16 text-center">
              {isRecording && (
                <div className="animate-ping-slow absolute inline-flex h-full w-full rounded-full bg-primary-300 opacity-70" />
              )}
              <button
                onClick={isRecording ? stopRecording : startRecording}
                className="relative rounded-full inline-flex"
              >
                <MicBtn className="w-16 text-primary-400 hover:text-primary-600 transition" />
              </button>
            </div>
          </div>
        </div>
        <button
          onClick={handleExitConversation}
          className="submit-btn bg-red-600 h-12 px-6 py-2 hover:bg-red-700"
        >
          대화 종료하기
        </button>
      </div>
    </AISpeakingBackgroundLayout>
  )
}

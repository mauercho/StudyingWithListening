'use client'

import ChatBubble from '@/app/_components/ChatBubble'
import { useEffect, useRef, useState } from 'react'
import { SubmitHandler, useForm } from 'react-hook-form'
import ChatSendBtn from '/public/icons/send.svg'
import { useParams } from 'next/navigation'
import { Client } from '@stomp/stompjs'
import { getToken } from '@/app/utils/cookie/getToken'
import useSWR from 'swr'

interface ChatMessage {
  sender: string
  message: string
  timestamp: string
}

export default function OnlineLearningChatPanel() {
  const [messages, setMessages] = useState<ChatMessage[]>(
    JSON.parse(sessionStorage.getItem('messages') || '[]')
  )
  const { data: userInfo } = useSWR('/users')
  const clientRef = useRef<Client | null>(null)
  const bottomRef = useRef<HTMLDivElement>(null)
  const params = useParams()
  const { lecture_id } = params
  const {
    register,
    handleSubmit,
    reset,
    formState: { isValid },
  } = useForm<{ chat: string }>()

  useEffect(() => {
    const accessToken = getToken()
    const stompClient = new Client({
      brokerURL: 'wss://ko-ala.site/api/lecture-chat/connections',
      connectHeaders: {
        Authorization: `Bearer ${accessToken}`,
      },
      reconnectDelay: 5000,
      onConnect: () => {
        console.log('채팅방 입장성공')
        stompClient.subscribe(
          `/api/lecture-chat/sub/${lecture_id}`,
          (message) => {
            if (message.body) {
              const receivedMessage = JSON.parse(message.body)
              setMessages((prevMessages) => [
                ...prevMessages,
                {
                  sender: receivedMessage.sender,
                  message: receivedMessage.message,
                  timestamp: new Date().toISOString(),
                },
              ])
            }
          }
        )
      },
      onDisconnect: () => {
        console.log('채팅방 퇴장성공')
      },
    })

    stompClient.activate()
    clientRef.current = stompClient

    return () => {
      stompClient.deactivate()
      clientRef.current = null
    }
  }, [lecture_id])

  useEffect(() => {
    bottomRef.current?.scrollIntoView({ behavior: 'smooth' })
    sessionStorage.setItem('messages', JSON.stringify(messages))
  }, [messages])

  const onSubmit: SubmitHandler<{ chat: string }> = (data) => {
    if (clientRef.current && clientRef.current.connected) {
      clientRef.current.publish({
        destination: `/api/lecture-chat/send/${lecture_id}`,
        body: JSON.stringify({
          sender: userInfo.data.nickname,
          message: data.chat,
        }),
      })
      reset()
    }
  }

  const handleKeyDown = (event: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (event.key === 'Enter' && !event.shiftKey) {
      event.preventDefault()
      handleSubmit(onSubmit)()
    }
  }

  return (
    <div className="h-full flex flex-col justify-between gap-6">
      <div className="overflow-auto">
        <div className="flex flex-col gap-3 pr-3">
          {messages.map((chat, index) => (
            <ChatBubble
              key={index}
              isMine={chat.sender === userInfo.data.nickname}
              message={chat.message}
              senderName={chat.sender}
              senderProfile={'/images/koala-sleep.png'}
              timeStamp={chat.timestamp}
            />
          ))}
        </div>
        <div ref={bottomRef} />
      </div>
      <form
        onSubmit={handleSubmit(onSubmit)}
        className="flex justify-between w-full gap-1 p-5 h-32 rounded-3xl bg-gray-100"
      >
        <textarea
          {...register('chat', { required: true })}
          onKeyDown={handleKeyDown}
          placeholder="메시지 보내기"
          className="bg-transparent w-full outline-none text-gray-900 resize-none"
        />
        <button
          type="submit"
          className={`text-gray-400 w-6 self-end ${isValid && 'text-primary-400'}`}
        >
          <ChatSendBtn />
        </button>
      </form>
    </div>
  )
}

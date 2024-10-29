import { useState, useRef } from 'react'
import { postSpeech } from '@/app/apis/googleSentence'

export function useSpeechRecognition() {
  const [isRecording, setIsRecording] = useState(false)
  const [transcription, setTranscription] = useState('')
  const mediaRecorderRef = useRef<MediaRecorder | null>(null)
  const audioChunksRef = useRef<Blob[]>([])

  const startRecording = async () => {
    const stream = await navigator.mediaDevices.getUserMedia({ audio: true })
    mediaRecorderRef.current = new MediaRecorder(stream)
    audioChunksRef.current = []

    mediaRecorderRef.current.ondataavailable = (event) => {
      audioChunksRef.current.push(event.data);
    };

    mediaRecorderRef.current.onstop = async () => {
      if (audioChunksRef.current.length > 0) {
        const audioBlob = new Blob(audioChunksRef.current, { type: 'audio/webm' })
        const audioBuff = await audioBlob.arrayBuffer()
        const buff = Buffer.from(audioBuff).toString('base64')
        const response = await postSpeech(buff)
        setTranscription(response)
      }
    };

    mediaRecorderRef.current.start()
    setIsRecording(true)
  };

  const stopRecording = () => {
    if (mediaRecorderRef.current) {
      mediaRecorderRef.current.stop()
    }
    setIsRecording(false)
  };

  return {
    isRecording,
    transcription,
    startRecording,
    stopRecording,
  }
}

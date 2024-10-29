'use client'

import React, { createContext, useState, useContext, ReactNode } from 'react'

// Sentence 타입 정의
interface Sentence {
  sentence_id: number
  sentence_text: string
  sentence_length: number
}

// Context의 타입 정의
interface SentenceContextType {
  sentences: Sentence[]
  updateSentences: (newSentences: Sentence[]) => void
}

// 기본값 정의
const defaultContextValue: SentenceContextType = {
  sentences: [],
  updateSentences: () => {}, // 기본값으로 빈 함수
}

// Context 생성
const SentenceContext = createContext<SentenceContextType>(defaultContextValue)

// Provider 컴포넌트 정의
export const SentenceProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [sentences, setSentences] = useState<Sentence[]>([])

  // 배열을 업데이트하는 함수
  const updateSentences = (newSentences: Sentence[]) => {
    setSentences(newSentences)
  }

  return (
    <SentenceContext.Provider value={{ sentences, updateSentences }}>
      {children}
    </SentenceContext.Provider>
  )
}

// Context를 사용하는 커스텀 훅
export const useSentences = () => {
  return useContext(SentenceContext)
}

// Context export
export default SentenceContext

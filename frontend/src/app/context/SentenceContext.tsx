'use client'

import React, { createContext, useState, useContext, ReactNode } from 'react'

interface Sentence {
  sentence_id: number
  sentence_text: string
  sentence_length: number
}

interface SentenceContextType {
  sentences: Sentence[]
  updateSentences: (newSentences: Sentence[]) => void
}

const defaultContextValue: SentenceContextType = {
  sentences: [],
  updateSentences: () => {},
}

const SentenceContext = createContext<SentenceContextType>(defaultContextValue)

export const SentenceProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [sentences, setSentences] = useState<Sentence[]>([])

  const updateSentences = (newSentences: Sentence[]) => {
    setSentences(newSentences)
  }

  return (
    <SentenceContext.Provider value={{ sentences, updateSentences }}>
      {children}
    </SentenceContext.Provider>
  )
}

export const useSentences = () => {
  return useContext(SentenceContext)
}

export default SentenceContext

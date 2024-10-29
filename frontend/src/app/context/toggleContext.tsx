'use client'

import React, { createContext, useState, useContext, ReactNode } from 'react'

interface result {
  toggled: boolean
  sentence_id: number
  user_sentence: string
}

interface ToggleContextType {
  dictationResult: result[]
  updateDictationResultAtIndex: (index: number, value: result) => void
}

const defaultContextValue: ToggleContextType = {
  dictationResult: new Array(10).fill({
    toggled: false,
    sentence_id: undefined,
    user_sentence: '',
  }),
  updateDictationResultAtIndex: () => {},
}

const DictationResultContext =
  createContext<ToggleContextType>(defaultContextValue)

export const DictationResultProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [dictationResult, setDictationResult] = useState<result[]>(
    new Array(10).fill({
      toggled: false,
      sentence_id: undefined,
      user_sentence: '',
    })
  )

  const updateDictationResultAtIndex = (index: number, value: result) => {
    setDictationResult((prevState) => {
      const newState = [...prevState]
      newState[index] = value
      return newState
    })
  }

  return (
    <DictationResultContext.Provider
      value={{ dictationResult, updateDictationResultAtIndex }}
    >
      {children}
    </DictationResultContext.Provider>
  )
}

export const useDictationResult = () => {
  return useContext(DictationResultContext)
}

export default DictationResultContext

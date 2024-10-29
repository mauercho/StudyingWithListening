'use client'

import Image from 'next/image'
import Correct from '/public/icons/correct.svg'
import Wrong from '/public/icons/wrong.svg'
import parse from 'html-react-parser'
import '@/app/styles/dictationResultStyle.css'
import { postUserSentence } from '@/app/apis/sentence'

interface GradingResultProps {
  result: {
    correct: boolean
    origin_text: string
    user_text: string
    result_tag: string
  }
  idx: number
}

export default function DictationGradingResult({
  result: { correct, origin_text, user_text, result_tag },
  idx,
}: GradingResultProps) {
  const handleClickSaveUserSentenceButton = async () => {
    const response = await postUserSentence('/sentences', {
      sentence_text: origin_text,
    })

    if (response.status === 201) {
      alert('문장이 잘 추가되었습니다')
    }
  }
  return (
    <div className="flex flex-col gap-6 relative w-full">
      <p className="absolute -left-10 -top-5">
        {correct ? <Correct /> : <Wrong />}
      </p>
      <div className="flex items-center gap-4 text-2xl font-normal">
        <p>{idx}.</p>
        <p>{origin_text}</p>
        <button
          onClick={handleClickSaveUserSentenceButton}
          className="bg-primary-400 py-1 px-3 rounded-full text-white text-sm"
        >
          복습 문장 추가
        </button>
      </div>

      {!correct ? (
        <div className="flex pl-10 gap-3">
          <p>틀린부분:</p>
          <div>
            {parse(result_tag, {
              replace: (domNode) => {
                if (domNode.type === 'tag' && domNode.attribs) {
                  if (domNode.attribs.classname || domNode.attribs.class) {
                    domNode.attribs.className = domNode.attribs.classname
                    delete domNode.attribs.classname
                  }
                }
                return domNode
              },
            })}
          </div>
        </div>
      ) : null}

      <div className="px-8 w-full">
        <div className="relative w-full py-2 bg-white border-t border-b border-primary-400 px-3">
          <div className="grid grid-cols-12">
            {new Array(36).fill(0).map((_, index) => (
              <div
                key={index}
                className="aspect-square border border-primary-400 flex justify-center items-center text-4xl text-center"
              >
                <p className="text-xl">{user_text.charAt(index)}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}

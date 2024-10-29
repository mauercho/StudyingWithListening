'use client'

import SentenceAddCard from '@/app/_components/SentenceAddCard'
import UploadIcon from '/public/icons/cloud-upload.svg'
import { useState } from 'react'
import { postSentenceImage } from '@/app/apis/sentence'
import Image from 'next/image'

export default function AddSentenceModal() {
  const [status, setStatus] = useState('initial')
  const [extractedSentence, setExtractedSentence] = useState<any>()

  const handleChangeImageFile = async (
    e: React.ChangeEvent<HTMLInputElement>
  ) => {
    const file = e.target.files?.[0]
    if (!file) return

    const formData = new FormData()
    formData.append('file', file, file.name)

    setStatus('loading')
    const response = await postSentenceImage('/images', formData)

    if (response?.status === 201) {
      setExtractedSentence(response.data)
      setStatus('done')
    } else {
      alert('문제가 발생했습니다. 다시 한 번 시도해주세요')
    }
  }

  const handleClickDeleteUserSentence = (index: number) => {
    const nExtractedSentence = [...extractedSentence]
    nExtractedSentence.splice(index, 1)

    setExtractedSentence(nExtractedSentence)
  }

  return (
    <div className="bg-white max-w-[60rem] min-w-80 max-h-[44rem] rounded-3xl p-12 flex flex-col">
      {status === 'initial' ? (
        <label htmlFor="imageUpload">
          <input
            type="file"
            onChange={handleChangeImageFile}
            className="hidden"
            id="imageUpload"
          />
          <div className="bg-primary-400 rounded-full py-4 px-6 text-center cursor-pointer text-white mx-auto flex gap-3 items-center shadow-md">
            <UploadIcon className="w-8" />
            추가할 문장을 업로드 해주세요.
          </div>
        </label>
      ) : status === 'loading' ? (
        <div className="flex flex-col items-center gap-2">
          <Image
            src="/images/spinner.gif"
            alt="spinner"
            width={80}
            height={80}
            className="w-20 h-20"
          />
          <p className="text-lg">분석중입니다....</p>
        </div>
      ) : (
        <>
          <h1 className="text-2xl text-gray-900">
            학습을 원하는 문장을 추가해주세요.
          </h1>
          <hr className="w-full my-4" />
          <div className="h-full overflow-auto">
            <ul className="flex flex-col gap-3 pr-3">
              {extractedSentence?.map((sentence: any, index: number) => (
                <SentenceAddCard
                  key={sentence.sentence_id}
                  sentenceId={sentence.sentence_id}
                  sentence={sentence.sentence_text}
                  handleDeleteuserSentence={() =>
                    handleClickDeleteUserSentence(index)
                  }
                />
              ))}
            </ul>
          </div>
        </>
      )}
    </div>
  )
}

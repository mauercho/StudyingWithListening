'use client'

import Image from 'next/image'
import Close from '/public/icons/close.svg'
import Add from '/public/icons/add.svg'
import { Dispatch, SetStateAction, useState } from 'react'
import { ImageList } from './CommunityPostEditor'

interface CommunityImageUploaderProps {
  imageList: ImageList[]
  setImageList: Dispatch<SetStateAction<ImageList[]>>
}

export default function CommunityImageUploader({
  imageList,
  setImageList,
}: CommunityImageUploaderProps) {
  const handleChangeFile = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]
    if (file) {
      const preview = URL.createObjectURL(file)
      let nImageList = [...imageList]
      nImageList.push({
        preview,
        file,
      })
      setImageList(nImageList)
    }
  }

  const handleClickDeleteImageButton = (
    e: React.MouseEvent<HTMLButtonElement>,
    index: number
  ) => {
    e.preventDefault()
    let nImageList = [...imageList]
    nImageList.splice(index, 1)
    setImageList(nImageList)
  }

  return (
    <div className="flex gap-6">
      {imageList.map((image, index) => {
        return (
          <div key={index} className="relative inline-block">
            <button
              onClick={(e) => handleClickDeleteImageButton(e, index)}
              className="absolute -top-2 -right-2 shadow-lg bg-white rounded-full w-8 h-8 flex justify-center items-center"
            >
              <Close />
            </button>
            <Image
              src={image.preview}
              width={96}
              height={96}
              className="w-24 h-24 aspect-square border rounded-lg object-cover"
              alt="image-preview"
              draggable={false}
              priority
            />
          </div>
        )
      })}
      {imageList.length < 5 ? (
        <div>
          <label
            htmlFor="file-upload"
            className="w-24 h-24 aspect-square border rounded-lg bg-gray-200 flex justify-center items-center cursor-pointer"
          >
            <Add />
          </label>
          <input
            id="file-upload"
            type="file"
            className="hidden"
            onChange={handleChangeFile}
          />
        </div>
      ) : null}
    </div>
  )
}

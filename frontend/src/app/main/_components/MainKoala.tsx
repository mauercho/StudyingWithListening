'use client'

import MainKoalaLevel from './MainKoalaLevel'
import MainKoalaNameEditor from './MainKoalaNameEditor'
import Image from 'next/image'
import useSWR from 'swr'
import { KoalaInfo, fetchKoalaInfo } from '@/app/apis/koala'

const KOALA_LEVEL_NAME = [
  {
    name: '애기코알라',
    imageUrl: '/images/koala-sleep.png',
  },
  {
    name: '유치원코알라',
    imageUrl: '/images/koala-kindergarten.png',
  },
  {
    name: '초등학생코알라',
    imageUrl: '/images/koala-pencil.png',
  },
  {
    name: '고등학생코알라',
    imageUrl: '/images/koala-business.png',
  },
  {
    name: '졸업코알라',
    imageUrl: '/images/koala-graduation.png',
  },
]

export default function MainKoala() {
  const { data: koalaInfo, error } = useSWR<KoalaInfo>(
    '/koalas',
    fetchKoalaInfo
  )

  return (
    <>
      {koalaInfo ? (
        <div className="flex flex-col items-center w-full relative">
          <MainKoalaNameEditor koalaName={koalaInfo?.koala_name} />
          <Image
            src={KOALA_LEVEL_NAME[koalaInfo.koala_level - 1].imageUrl}
            width={352}
            height={0}
            className="w-[22rem] mt-20"
            alt="koala"
            draggable="false"
          />
          <MainKoalaLevel
            percent={koalaInfo?.koala_exp}
            level={koalaInfo.koala_level}
          />
        </div>
      ) : null}
    </>
  )
}

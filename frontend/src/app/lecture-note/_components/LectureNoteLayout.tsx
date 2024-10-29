import React from 'react'
import Image from 'next/image'

export default function LectureNoteLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="h-full relative">
      {children}
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[15%] -left-[3%] top-[30%] -z-10"
        alt="cloud"
        src="/images/cloud.svg"
      />
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[10%] right-[25%] top-[3%] scale-x-[-1] -z-10"
        alt="cloud"
        src="/images/cloud.svg"
      />
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[25%] right-0 top-[45%] -z-10"
        alt="cloud"
        src="/images/cloud.svg"
      />
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[8%] right-[5%] top-[38%] -z-10"
        alt="cloud"
        src="/images/astronaut.png"
      />
    </div>
  )
}

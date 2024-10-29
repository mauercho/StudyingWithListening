'use client'

import Image from 'next/image'
import Link from 'next/link'

export default function MainCommunityButton() {
  return (
    <Link href="/community">
      <div className="inline-block relative w-auto">
        <span className="whitespace-nowrap py-2 px-5 select-none cursor-pointer absolute -top-6 bg-[#FF7A7A] text-white text-sm  rounded-full flex justify-center items-center font-semibold z-0">
          커뮤니티
        </span>
        <button className="flex justify-center items-center bg-[#ffffff] w-24 h-24 rounded-full shadow-lg cursor-pointer transition-transform duration-200 ease-in-out">
          <Image
            src="/images/toy.png"
            width={0}
            height={0}
            sizes="100%"
            className="w-3/4 h-3/4 rounded-full"
            alt="eucalyptus"
            draggable="false"
          />
        </button>
      </div>
    </Link>
  )
}

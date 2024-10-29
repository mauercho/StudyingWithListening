import Image from 'next/image'
import Link from 'next/link'

export default function NotFound() {
  return (
    <div className="flex flex-col justify-center items-center h-main-screen -translate-y-20 gap-4">
      <Image
        src="/images/404-logo.svg"
        width={600}
        height={600}
        alt="koala-sleep"
        className="translate-x-4"
      />
      <Image
        src="/images/404-text.svg"
        width={400}
        height={400}
        alt="koala-sleep"
      />
      <div className="mt-4 text-xl flex flex-col">
        <Link
          href="/main"
          className="mt-20 px-12 py-2 text-gray-700  rounded-full border border-gray-700 shadow-md"
        >
          메인페이지로 가기
        </Link>
      </div>
    </div>
  )
}

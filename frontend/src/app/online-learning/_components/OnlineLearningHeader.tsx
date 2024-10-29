import Link from 'next/link'
import VideoIcon from '/public/icons/video-square.svg'
import Logo from '/public/images/logo.svg'
import { useSearchParams } from 'next/navigation'

export default function OnlineLearningHeader() {
  const searchParams = useSearchParams()
  const title = searchParams.get('title')
  const teacher_name = searchParams.get('teacher_name')
  return (
    <header className="fixed h-20 w-full z-50 px-9 flex items-center">
      <Link href="/main">
        <h1 className="flex gap-2 text-primary-400">
          <VideoIcon className="w-6" />
          <Logo className="h-7" />
        </h1>
      </Link>
      <span className="p-3">|</span>
      <p className="text-primary-900 flex gap-2 items-end whitespace-nowrap">
        <span className="text-bold text-lg">{title}</span>
        <span className="leading-[1.65rem]">{teacher_name} 선생님</span>
      </p>
    </header>
  )
}

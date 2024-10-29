import Image from 'next/image'

export default function ReviewLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="overflow-x-hidden relative">
      {children}
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="w-[12%] absolute right-[24%] top-[8%] -z-20"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="w-[12%] h-auto absolute top-[20%] right-[0%] translate-x-10 -z-20"
      />
      <Image
        src="/images/koala-sleep.png"
        alt="cloud"
        width={200}
        height={200}
        priority
        className="w-[6%] absolute top-[15%] right-[2%] -z-10"
      />
    </div>
  )
}

import Image from 'next/image'

export default function OnlineLearningLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="relative overflow-hidden h-main-screen">
      {children}
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="absolute top-[17%] left-[4%] w-[18%] h-[18%] -z-10"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="absolute top-[26%] right-[7%] w-[10%] h-[10%] -z-10"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="absolute bottom-[8%] right-[0%] w-[10%] h-[10%] translate-x-[15%] -z-10"
      />
    </div>
  )
}

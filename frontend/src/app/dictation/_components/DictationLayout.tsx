import Image from 'next/image'

export default function DictationLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="relative w-full h-main-screen flex items-center justify-center">
      {children}
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[12%] top-[5%] right-[1%] -z-10"
        alt="cloud"
        src="/images/cloud.svg"
        draggable="false"
      />
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[15%] bottom-[10%] -left-[1%] -z-10 scale-x-[-1]"
        alt="cloud"
        src="/images/cloud.svg"
        draggable="false"
      />
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[12%] bottom-[1%] right-[15%] -z-10 scale-x-[-1]"
        alt="cloud"
        src="/images/cloud.svg"
        draggable="false"
      />
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[8%] top-[30%] left-[10%] -z-10 scale-x-[-1]"
        alt="astronaut"
        src="/images/astronaut.png"
        draggable="false"
      />
    </div>
  )
}

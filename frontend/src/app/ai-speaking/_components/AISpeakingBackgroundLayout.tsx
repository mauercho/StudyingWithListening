import Image from 'next/image'

export default function AISpeakingBackgroundLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="overflow-hidden relative">
      {children}
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        priority
        className="absolute top-[16%] left-[6%] w-[12%]"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        priority
        className="absolute top-[14%] right-[5%] w-[10%]"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        priority
        className="absolute bottom-[4%] w-[16%] right-[-2%]"
      />
      <Image
        src="/images/koala-sleep.png"
        alt="koala-sleep"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="absolute top-[10%] right-[8%] w-[6%]"
      />
    </div>
  )
}

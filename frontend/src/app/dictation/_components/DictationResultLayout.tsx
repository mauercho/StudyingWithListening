import Image from 'next/image'

export default function DictationResultLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="h-auto overflow-hidden w-full relative">
      {children}
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={240}
        height={240}
        priority
        className="absolute bottom-[15%] left-[10%] -z-10 w-[15%]"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={150}
        height={150}
        priority
        className="absolute top-[30%] -right-10 -z-10 w-[15%]"
      />
    </div>
  )
}

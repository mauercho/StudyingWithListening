import Image from 'next/image'

export default function ReportLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="relative w-full h-full overflow-x-hidden">
      {children}
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="absolute top-[12%] left-[1%] w-[14%] h-auto"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="absolute top-[50%] left-[0%] w-[20%] translate-x-[-30%] h-auto"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="absolute top-[28%] right-[0%] w-[20%] h-auto translate-x-[14%]"
      />
    </div>
  )
}

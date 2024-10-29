import Image from 'next/image'

export default function MainLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="w-full h-auto min-h-main-height overflow-hidden">
      {children}
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="w-[17%] h-auto absolute top-[10%] left-[9%] z-0"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="w-[13%] h-auto absolute top-[43%] left-[22%] z-0"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="w-[13%] h-auto absolute top-[21%] left-[47%] z-0"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={0}
        height={0}
        sizes="100%"
        priority
        className="w-[20%] h-auto absolute top-[36%] right-[7%] z-0"
      />
    </div>
  )
}

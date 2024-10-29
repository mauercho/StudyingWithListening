import Image from 'next/image'

export default function CommunityLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="flex justify-center relative overflow-hidden">
      <div>{children}</div>
      <div className="absolute w-[15%] aspect-video top-[6%] left-[30%] -z-10">
        <Image
          width={0}
          height={0}
          sizes="100%"
          className="w-full absolute"
          alt="cloud"
          src="/images/cloud.svg"
        />
        <Image
          width={0}
          height={0}
          sizes="100%"
          className="absolute w-[50%] left-1/4 -top-[10px]"
          alt="koala"
          src="/images/koala-sleep.png"
        />
      </div>
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[12%] top-[20%] right-[1%] -z-10 scale-x-[-1]"
        alt="cloud"
        src="/images/cloud.svg"
      />
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[15%] top-[50%] left-[-5%] -z-10 scale-x-[-1]"
        alt="cloud"
        src="/images/cloud.svg"
      />
      <Image
        width={0}
        height={0}
        sizes="100%"
        className="absolute w-[15%] top-[80%] right-[-3%] -z-10 scale-x-[-1]"
        alt="cloud"
        src="/images/cloud.svg"
      />
    </div>
  )
}

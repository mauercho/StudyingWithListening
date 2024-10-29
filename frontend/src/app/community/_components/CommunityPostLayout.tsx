import Image from 'next/image'

export default function CommunityPostLayout({ children }: any) {
  return (
    <div className="h-auto overflow-hidden w-full relative min-h-main-height">
      {children}
      <div className="absolute w-[20%] top-[2%] left-[20%]">
        <Image
          src={'/images/cloud.svg'}
          width={0}
          height={0}
          className="w-full absolute top-0 -z-10"
          alt="cloud"
          draggable={false}
          priority
        />
        <Image
          src={'/images/koala-sleep.png'}
          width={0}
          height={0}
          sizes="100%"
          className="w-[50%] absolute -top-10 -z-10 right-1/4"
          alt="koala"
          draggable={false}
          priority
        />
      </div>
      <Image
        src={'/images/cloud.svg'}
        width={0}
        height={0}
        sizes="100%"
        className="w-[10%] absolute right-[1%] bottom-[60%]"
        alt="cloud"
        draggable={false}
        priority
      />
      <Image
        src={'/images/cloud.svg'}
        width={0}
        height={0}
        sizes="100%"
        className="w-[10%] absolute -left-[2%] bottom-1/4 -scale-x-[1]"
        alt="cloud"
        draggable={false}
        priority
      />
      <Image
        src={'/images/astronaut.png'}
        width={0}
        height={0}
        sizes="100%"
        className="w-[10%] absolute right-7 bottom-[30%] -z-10"
        alt="astronaut"
        draggable={false}
        priority
      />
    </div>
  )
}

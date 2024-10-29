import Image from 'next/image'

export default function AuthLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <div className="relative h-main-screen flex justify-center items-center overflow-hidden">
      {children}
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={240}
        height={240}
        priority
        className="absolute bottom-10 left-10 -z-20"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={200}
        height={200}
        priority
        className="absolute top-40 right-0 translate-x-10"
      />
      <Image
        src="/images/cloud.svg"
        alt="cloud"
        width={150}
        height={150}
        priority
        className="absolute top-10 left-2/3 translate-x-10"
      />
      <Image
        src="/images/koala-sleep.png"
        alt="koala-sleep"
        width={100}
        height={100}
        priority
        className="absolute top-2 left-2/3 translate-x-14"
      />
      <Image
        src="/images/koala-tree.png"
        alt="koala-tree"
        width={380}
        height={380}
        priority
        className="absolute bottom-0 translate-y-2 left-40 -z-10"
      />
    </div>
  )
}

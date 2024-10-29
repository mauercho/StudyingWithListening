import Image from 'next/image'

export default function BasicModal() {
  return (
    <div className="w-[30rem] h-80 bg-white rounded-3xl flex flex-col justify-center items-center gap-10">
      <Image
        src="/images/astronaut.png"
        alt="astronaut"
        width={0}
        height={0}
        sizes="100%"
        className="w-24"
      />
      <p>
        커뮤니티에서는 <span className="text-primary-400 text-lg">‘한글’</span>
        만 써주세요!
      </p>
    </div>
  )
}

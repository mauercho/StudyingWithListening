import Image from 'next/image'
import GradingResult from '@/app/dictation/_components/DictationGradingResult'

interface result {
  correct: boolean
  origin_text: string
  user_text: string
  result_tag: string
}

export default function GradingSheet({ resultList = [] }: any) {
  let sum = 0
  resultList.map((result: any) => {
    if (result.correct) {
      sum += 10
    }
  })

  return (
    <div className="w-full max-w-[58rem] relative px-24 pt-20 pb-32 gap-20 flex flex-col items-center h-auto bg-[url('/images/texture.png')]">
      <div className="flex items-end">
        <p className="text-[#D14848] text-6xl font-black">{sum}</p>
        <p className="text-primary-900 text-xl font-medium">/100</p>
      </div>
      <div className="flex flex-col gap-16 w-full">
        {resultList.map((result: result, index: number) => {
          return (
            <GradingResult
              result={result}
              idx={index + 1}
              key={result.origin_text}
            />
          )
        })}
      </div>
      <Image
        src="/images/cloud.svg"
        alt="astronaut"
        width={0}
        height={0}
        sizes="100%"
        className="w-60 h-32 absolute -top-16 -right-36"
      />
      <Image
        src="/images/cloud.svg"
        alt="astronaut"
        width={0}
        height={0}
        sizes="100%"
        className="w-40 h-20 absolute top-20 -left-20"
      />
      <Image
        src="/images/astronaut.png"
        alt="astronaut"
        width={0}
        height={0}
        sizes="100%"
        className="w-36 h-36 absolute -bottom-20 -right-20"
      />
    </div>
  )
}

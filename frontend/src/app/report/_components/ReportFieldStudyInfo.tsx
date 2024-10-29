import Image from 'next/image'

export interface StudyInfoProps {
  writing: number
  speaking: number
  lectures: number
}

export default function ReportFieldStudyInfo({
  writing,
  speaking,
  lectures,
}: StudyInfoProps) {
  return (
    <div className="flex after:xl:flex-grow w-full">
      <div className="max-w-[15rem] w-full h-auto bg-primary-400 rounded-2xl md:p-2 xl:px-5 flex-grow mr-4">
        <div className="flex md:flex-col xl:flex-row items-center w-full h-full md:gap-3">
          <div className="flex items-center max-w-16 min-w-5 max-h-16 bg-white rounded-full p-2">
            <Image
              src="/icons/report-dictation.svg"
              alt="dictationIcon"
              width={40}
              height={40}
              className="w-full h-full"
            />
          </div>
          <div className="text-white w-full text-center">
            <p className="md:text-xs xl:text-base break-words">
              받아쓰기 문장 수
            </p>
            <p className="font-bold text-2xl mt-2">{writing}</p>
          </div>
        </div>
      </div>
      <div className="max-w-[15rem] w-full h-auto bg-primary-400 rounded-2xl md:p-2 xl:px-5 flex-grow mr-4">
        <div className="flex md:flex-col xl:flex-row items-center w-full h-full md:gap-3">
          <div className="flex items-center max-w-16 min-w-5 max-h-16 bg-white rounded-full p-2">
            <Image
              src="/icons/report-ai-conversation.svg"
              alt="dictationIcon"
              width={40}
              height={40}
              className="w-full h-full"
            />
          </div>
          <div className="text-white w-full text-center">
            <p className="md:text-xs xl:text-base break-words">
              AI회화 진행 시간
            </p>
            <p className="font-bold text-2xl mt-2">{speaking}</p>
          </div>
        </div>
      </div>
      <div className="max-w-[15rem] w-full h-auto bg-primary-400 rounded-2xl md:p-2 xl:px-5 flex-grow">
        <div className="flex md:flex-col xl:flex-row items-center w-full h-full md:gap-3">
          <div className="flex items-center max-w-16 min-w-5 max-h-16 bg-white rounded-full p-2">
            <Image
              src="/icons/report-online-learning.svg"
              alt="dictationIcon"
              width={40}
              height={40}
              className="w-full h-full"
            />
          </div>
          <div className="text-white w-full text-center">
            <p className="md:text-xs xl:text-base break-words">학습한 강의 수</p>
            <p className="font-bold text-2xl mt-2">{lectures}</p>
          </div>
        </div>
      </div>
    </div>
  )
}

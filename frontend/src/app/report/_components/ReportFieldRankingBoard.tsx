import Image from 'next/image'

interface Ranking {
  userId: number
  nickname: string
  ranking: number
}

interface RankingProps {
  rankingData: Ranking[]
  myRank: number
}

export default function ReportFieldRankingBoard({
  rankingData,
  myRank,
}: RankingProps) {
  return (
    <div className="flex flex-grow lg:flex-col md:flex-row relative md:w-1/2 lg:w-full h-auto bg-white rounded-2xl shadow-md">
      <div className="relative lg:w-full md:w-1/2 lg:h-1/2 md:h-full bg-primary-50 md:rounded-l-2xl lg:rounded-bl-none lg:rounded-t-2xl p-4">
        <p className="text-xl text-primary-400 mb-2.5">랭킹</p>
        <div className="max-w-[10rem] min-w-[5rem] h-auto bg-white rounded-full p-6 mx-auto">
          <Image
            src="/images/koala-front.png"
            alt="Koala"
            width={100}
            height={100}
            className="w-full h-auto"
          />
        </div>
        <div className="absolute lg:hidden flex bottom-0 left-1/2  transform -translate-x-1/2 -translate-y-1/2 w-4/5 h-[2rem] bg-gradient-to-b from-yellow-300 to-yellow-500 rounded-full">
          <p className="m-auto text-center text-white text-base">{myRank}위</p>
        </div>
      </div>
      <div className="absolute md:hidden lg:flex top-1/2 left-1/2  transform -translate-x-1/2 -translate-y-1/2 w-1/2 aspect-[3.5] bg-gradient-to-b from-yellow-300 to-yellow-500 rounded-full">
        <p className="m-auto text-center text-white text-xl">{myRank}위</p>
      </div>
      <div className="flex flex-col flex-grow justify-center lg:px-3 md:h-full lg:h-1/2">
        <div className="inline-flex p-4 w-full">
          <div className="flex w-6 h-6 bg-gray-100 rounded-full my-auto border border-solid mr-4">
            <p className="m-auto text-sm text-gray-500">1</p>
          </div>
          <div className="w-10 h-10 border border-solid border-gray-100 rounded-full mr-2 md:hidden lg:flex">
            <Image
              src="/images/koala-front.png"
              alt="Koala"
              width={100}
              height={100}
              className="w-full h-auto rounded-full m-auto"
            />
          </div>
          <div className="flex flex-col md:justify-center xl:justify-between">
            <p className="text-sm">
              {rankingData[0] ? rankingData[0].nickname : ''}
            </p>
            <p className="text-gray-400 text-xs md:hidden lg:inline">
              {rankingData[0] ? '영광의 1위!' : '아직 1위가 없어요'}
            </p>
          </div>
        </div>
        <div className="inline-flex p-4 w-full">
          <div className="flex w-6 h-6 bg-gray-100 rounded-full my-auto border border-solid border-gray-200 mr-4">
            <p className="m-auto text-sm text-gray-500">2</p>
          </div>
          <div className="w-10 h-10 border border-solid border-gray-100 rounded-full mr-2 md:hidden lg:flex">
            <Image
              src="/images/koala-front.png"
              alt="Koala"
              width={100}
              height={100}
              className="w-full h-auto rounded-full m-auto"
            />
          </div>
          <div className="flex flex-col md:justify-center xl:justify-between">
            <p className="text-sm">
              {rankingData[1] ? rankingData[1].nickname : ''}
            </p>
            <p className="text-gray-400 text-xs md:hidden lg:inline">
              {rankingData[1] ? '멋져요!' : '아직 2위가 없어요'}
            </p>
          </div>
        </div>
        <div className="inline-flex p-4 w-full">
          <div className="flex w-6 h-6 bg-gray-100 rounded-full my-auto border border-solid border-gray-200 mr-4">
            <p className="m-auto text-sm text-gray-500">3</p>
          </div>
          <div className="w-10 h-10 border border-solid border-gray-100 rounded-full mr-2 md:hidden lg:flex">
            <Image
              src="/images/koala-front.png"
              alt="Koala"
              width={100}
              height={100}
              className="w-full h-auto rounded-full m-auto"
            />
          </div>
          <div className="flex flex-col md:justify-center xl:justify-between">
            <p className="text-sm">
              {rankingData[2] ? rankingData[2].nickname : ''}
            </p>
            <p className="text-gray-400 text-xs md:hidden lg:inline">
              {rankingData[2] ? '지금처럼 열심히 하기!' : '아직 3위가 없어요'}
            </p>
          </div>
        </div>
      </div>
    </div>
  )
}

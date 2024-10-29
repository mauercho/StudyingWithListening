import Image from 'next/image'
import MainLayout from '@/app/main/_components/MainLayout'
import EducationCardList from '@/app/main/_components/MainEducationCardList'
import EucalyptusButton from '@/app/main/_components/MainEucalyptusButton'
import MainKoala from './_components/MainKoala'
import MainCommunityButton from './_components/MainCommunityButton'

export default function Main() {
  return (
    <MainLayout>
      <div className="w-full h-auto min-h-main-height overflow-hidden relative select-none z-10 flex flex-col items-center">
        <div className="relative w-screen h-full min-h-main-height flex max-w-[94rem]">
          <div className="w-2/5 h-1/2 min-h-[32rem] self-end ml-12 mb-8 relative flex items-end justify-center">
            <div className="flex flex-col gap-6 absolute top-0 -left-10 z-10">
              <MainCommunityButton />
              <EucalyptusButton />
            </div>
            <MainKoala />
          </div>

          <div className="absolute -right-28 top-1/3">
            <EducationCardList />
          </div>
        </div>
        <div className=" w-full h-full absolute top-[90%] left-24 -z-50">
          <Image
            src="/images/ground.png"
            width={0}
            height={0}
            sizes="100%"
            className="scale-[2] w-full"
            alt="ground"
            draggable="false"
          />
        </div>
      </div>
    </MainLayout>
  )
}

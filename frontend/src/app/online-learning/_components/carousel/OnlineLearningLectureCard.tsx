import Image from 'next/image'
import Link from 'next/link'
import { LectureCard } from '@/app/apis/online-learning'

interface CardProps extends LectureCard {
  cardVariant: string
}

export default function OnlineLearningLectureCard({
  cardVariant,
  ...cardData
}: CardProps) {
  const {
    lecture_id,
    teacher_name,
    lecture_title,
    lecture_detail,
    lecture_schedule,
    lecture_img_url,
  } = cardData
  if (cardVariant === 'Back') {
    return (
      <div className="relative w-full h-full rounded-tl-3xl overflow-hidden shadow-mg">
        <div className="absolute w-full h-full">
          <Image
            src={lecture_img_url}
            alt="Lecture Card"
            width={200}
            height={200}
            className="w-full h-full object-cover blur-2xl"
          />
        </div>
        <div className="relative bg-white rounded-tl-3xl h-full mt-5 mx-5 px-6 md:pt-2 lg:pt-6">
          <p className="text-primary-900 font-medium break-words text-center md:truncate md:text-lg md:mb-[0.5rem] lg:text-xl lg:mb-[1rem]">
            {lecture_title}
          </p>
          <div className="w-full aspect-square overflow-hidden mb-[0.5rem]">
            <p className="text-primary-900 overflow-hidden break-words md:text-sm lg:text-base">
              {lecture_detail}
            </p>
          </div>
          <div className="flex text-primary-900 md:text-xs lg:text-sm md:mb-[0.5rem] lg:mb-[1.25rem]">
            <p className="mr-2.5 font-medium">수업일정</p>
            <p>{lecture_schedule}</p>
          </div>
          <Link
            href={`online-learning/${lecture_id}?title=${lecture_title}&teacher_name=${teacher_name}`}
            className="max-w-[14rem] min-w-[10rem]"
          >
            <button
              onClick={(e) => e.stopPropagation()}
              className="bg-primary-400 w-full aspect-[5/1] rounded-3xl text-white font-medium md:text-sm lg:text-base"
            >
              강의 입장하기
            </button>
          </Link>
        </div>
      </div>
    )
  } else {
    return (
      <div className="w-full h-full bg-white rounded-tr-3xl overflow-hidden shadow-lg cursor-pointer">
        <div className="w-full aspect-square r">
          <Image
            src={lecture_img_url}
            alt="Lecture Card"
            width={300}
            height={300}
            className="w-full h-full object-cover"
          />
        </div>
        <div className="m-auto py-[1.25rem]">
          <p className="text-primary-900 text-xl font-medium text-center mb-2 mx-5 break-words truncate">
            {lecture_title}
          </p>
          <p className="text-primary-900 text-base text-center font-regular">
            <span className="font-medium mr-1">{teacher_name}</span>
            선생님
          </p>
        </div>
      </div>
    )
  }
}

import Image from 'next/image'
import Link from 'next/link'

interface LectureNoteListElementProps {
  lectureId: number
  teacherName: string
  lectureTitle: string
  lectureNoteCounts: number
  lectureImgUrl: string
}

export default function LectureNoteElement({
  lectureId,
  teacherName,
  lectureTitle,
  lectureNoteCounts,
  lectureImgUrl,
}: LectureNoteListElementProps) {
  return (
    <div className="border-b-[1px] border-gray-200 bg-white w-full flex py-5 px-8 items-center">
      <div className="flex flex-col w-full gap-2">
        <Link
          href={{
            pathname: `/lecture-note/${lectureId}`,
            query: {
              lectureTitle,
              lectureTeacher: teacherName,
            },
          }}
        >
          <p className="text-xl font-medium">{lectureTitle}</p>
        </Link>
        <div className="flex items-center gap-3 text-base text-primary-900">
          <p>노트수 {lectureNoteCounts}개</p>
          <hr className="w-[1px] bg-primary-900 h-3" />
          <p>{teacherName} 선생님</p>
        </div>
      </div>
      <Image
        src={lectureImgUrl}
        width={160}
        height={80}
        alt="lecture"
        className="w-40 h-20 border rounded-lg aspect-video"
        priority
      />
    </div>
  )
}

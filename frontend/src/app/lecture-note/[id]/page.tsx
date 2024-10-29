import LectureNoteLayout from '@/app/lecture-note/_components/LectureNoteLayout'
import LectureNoteBookmark from '../_components/LectureNoteBookmark'
import Image from 'next/image'
import { getLectureNote } from '@/app/apis/lecture'
import Link from 'next/link'

interface LectureNoteDetailProps {
  params: { id: string }
  searchParams: {
    lectureTitle: string
    lectureTeacher: string
  }
}

export default async function LectureNoteDetail({
  params: { id },
  searchParams: { lectureTitle = '한국어 강의', lectureTeacher = '선생님' },
}: LectureNoteDetailProps) {
  const lectureNoteList = await getLectureNote(`/lectures/${id}/note`)

  return (
    <LectureNoteLayout>
      <div className="flex flex-col items-center gap-6 h-full pt-20 min-h-screen">
        <div className="flex items-center pl-3 max-w-[65rem] w-full gap-2">
          <Image
            src="/icons/arrow-right-circle.svg"
            width={0}
            height={0}
            alt="teacher"
            sizes="100%"
            className="w-5 h-5"
          />
          <Link href="/lecture-note">
            <p className="text-gray-500 text-xl font-normal">
              강의노트 리스트로 가기
            </p>
          </Link>
        </div>

        <div className="h-auto bg-white pt-12 pb-36 px-16 w-full max-w-[65rem] rounded-t-[4rem] flex-grow">
          <div className="flex flex-col gap-1 items-center pb-10  border-b-[1px] border-gray-900">
            <h1 className="text-gray-900 text-3xl font-medium">
              {lectureTitle}
            </h1>
            <h3 className="text-gray-900 text-lg font-normal">
              {lectureTeacher} 선생님
            </h3>
          </div>

          <div className="mt-14 flex flex-col gap-8">
            {lectureNoteList.data ? (
              lectureNoteList.data.map((lectureNote: any) => {
                return (
                  <LectureNoteBookmark
                    key={lectureNote.note_id}
                    noteId={lectureNote.note_id}
                    noteTitle={lectureNote.note_title}
                    noteContent={lectureNote.note_content}
                    noteCreatedAt={lectureNote.note_created_at}
                  />
                )
              })
            ) : (
              <div className="flex flex-col items-center justify-center">
                <Image
                  src="/images/koala-pencil.png"
                  width={96}
                  height={96}
                  alt="koala"
                  className="w-24"
                  priority
                />
                <p className="text-lg text-gray-500">강의 노트가 없습니다</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </LectureNoteLayout>
  )
}

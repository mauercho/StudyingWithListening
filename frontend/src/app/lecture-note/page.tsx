import { getLectureList } from '../apis/lecture'
import LectureNoteElement from './_components/LectureNoteElement'
import LectureNoteLayout from './_components/LectureNoteLayout'

export default async function LectureNoteMain() {
  const lectureListData = await getLectureList(`/lectures`)
  const { lecture_list: lectureList } = lectureListData

  return (
    <LectureNoteLayout>
      <div className="flex flex-col items-center gap-7 h-full pt-20 min-h-screen">
        <h1 className="text-3xl font-medium text-primary-900">강의 노트</h1>
        <div className="h-auto bg-white pt-10 pb-36 px-16 w-full max-w-[65rem] rounded-t-[4rem] flex-grow">
          {lectureList?.map((lectureNote) => {
            return (
              <LectureNoteElement
                key={lectureNote.lecture_id}
                lectureId={lectureNote.lecture_id}
                teacherName={lectureNote.teacher_name}
                lectureTitle={lectureNote.lecture_title}
                lectureNoteCounts={lectureNote.lecture_note_count}
                lectureImgUrl={lectureNote.lecture_img_url}
              />
            )
          })}
        </div>
      </div>
    </LectureNoteLayout>
  )
}

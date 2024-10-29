import Title from '@/app/_components/Title'
import OnlineLearningLectureCardSlide from './_components/carousel/OnlineLearningLectureCardSlide'
import OnlineLearningLayout from '@/app/online-learning/_components/OnlineLearningLayout'

export default function OnlineLearningMain() {
  return (
    <OnlineLearningLayout>
      <div className="my-12">
        <Title
          CourseTitle="화상 수업"
          CourseDescription="한국인 강사와 함께 한국어를 즐겁게 배워보아요!"
        />
      </div>
      <OnlineLearningLectureCardSlide />
    </OnlineLearningLayout>
  )
}

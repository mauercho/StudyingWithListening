import { Suspense } from 'react'
import Title from '../_components/Title'
import AISpeakingBackgroundLayout from './_components/AISpeakingBackgroundLayout'
import AISpeakingCategoryList from './_components/AISpeakingCategoryList'
import AISpeakingSlider from './_components/AISpeakingSlider'

export default function AISpeakingMain() {
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <AISpeakingBackgroundLayout>
        <div className="h-main-screen flex flex-col items-center">
          <div className="mt-12">
            <Title
              CourseTitle="AI 회화"
              CourseDescription="다양한 상황에서 생길 수 있는 대화를  AI와 함께 학습하세요!"
            />
          </div>
          <AISpeakingCategoryList />
          <AISpeakingSlider />
        </div>
      </AISpeakingBackgroundLayout>
    </Suspense>
  )
}

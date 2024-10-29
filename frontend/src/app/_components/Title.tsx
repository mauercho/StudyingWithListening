interface TitleProps {
  CourseTitle: String
  CourseDescription: String
}

export default function Title({ CourseTitle, CourseDescription }: TitleProps) {
  return (
    <header>
      <h1 className="text-center text-3xl text-gray-800 font-medium">
        {CourseTitle}
      </h1>
      <p className="text-center mt-2 text-xl text-gray-800">
        {CourseDescription}
      </p>
    </header>
  )
}

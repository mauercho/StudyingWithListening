'use client'

interface ButtonProps {
  content:string
  onClick: () => void
}

export default function ReviewMenuButton({ content, onClick }: ButtonProps) {
  
  const handleClick = () => {
    onClick()
  }
  
  return (
    <button onClick={handleClick} className="w-full px-10 py-4 bg-primary-400 text-white rounded-[4rem] mb-2.5 shadow-md active:bg-gray-300 whitespace-nowrap">
      {content}
    </button>
  )
}
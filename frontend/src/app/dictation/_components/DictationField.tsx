import { useEffect, useRef, useState } from 'react'

export default function DictationField({
  userAnswer: { userAnswer, setUserAnswer },
}: any) {
  const [grid] = useState(Array(36).fill(''))
  const hiddenInputRef = useRef<HTMLInputElement | null>(null)
  const [hiddenValue, setHiddenValue] = useState('')
  const [currentIndex, setCurrentIndex] = useState<number | undefined>()

  const handleHiddenInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const index = e.target.selectionStart ?? 0

    setHiddenValue(e.target.value)
    setCurrentIndex(index)
    setUserAnswer(e.target.value)
  }

  const handleFocus = () => {
    const index = hiddenInputRef.current?.selectionStart ?? 0
    setCurrentIndex(index)
  }

  useEffect(() => {
    const inputElement = hiddenInputRef.current
    if (inputElement) {
      inputElement.addEventListener('focus', handleFocus)
      inputElement.addEventListener('click', handleFocus)
      return () => {
        inputElement.removeEventListener('focus', handleFocus)
        inputElement.removeEventListener('click', handleFocus)
      }
    }
  }, [hiddenInputRef.current])

  useEffect(() => {
    if (userAnswer.length === 0) {
      setHiddenValue('')
    }
  }, [userAnswer])

  useEffect(() => {
    hiddenInputRef.current?.focus()
  }, [hiddenInputRef])

  useEffect(() => {
    if (hiddenInputRef.current && hiddenInputRef.current.selectionStart) {
      const index = hiddenInputRef.current.selectionStart - 1 ?? 0
      setCurrentIndex(index)
    }
  })

  return (
    <div className="flex flex-col gap-3">
      <input
        ref={hiddenInputRef}
        onChange={handleHiddenInputChange}
        type="text"
        id="hiddenInput"
        value={hiddenValue}
        className="absolute -left-full opacity-0"
      />
      <div className="relative flex justify-center overflow-hidden w-[1040px] py-[10px] bg-white border-t-[1px] border-b-[1px] border-primary-400">
        <div className="grid grid-cols-1 -left-10 absolute">
          {new Array(3).fill(0).map((_, index) => {
            return (
              <div
                key={index}
                className={`cursor-text relative flex justify-center items-center w-20 h-20 outline outline-1 outline-primary-400 text-4xl text-center `}
              />
            )
          })}
        </div>
        <div className="grid grid-cols-12">
          {grid.map((_, index) => (
            <div
              key={index}
              className={`cursor-text relative flex justify-center items-center w-20 h-20 outline outline-1 outline-primary-400 text-4xl text-center`}
              onClick={() => {
                if (hiddenValue.length === 0) {
                  hiddenInputRef.current?.focus()
                  hiddenInputRef.current?.setSelectionRange(
                    index + 1,
                    index + 1
                  )
                  setCurrentIndex(index)
                }
                if (index > hiddenValue.length - 1) {
                  return
                }
                hiddenInputRef.current?.focus()
                hiddenInputRef.current?.setSelectionRange(index + 1, index + 1)
                setCurrentIndex(index)
              }}
            >
              <p
              // className={`${index === currentIndex ? 'bg-gray-500 text-white' : ''}`}
              >
                {hiddenValue.charAt(index)}
              </p>

              <hr
                className={`${index === currentIndex ? 'block animate-blink' : 'hidden'} absolute right-2 h-[50px] border-none w-[2px] bg-black`}
              />
            </div>
          ))}
        </div>
        <div className="grid grid-cols-1 -right-10 absolute">
          {new Array(3).fill(0).map((_, index) => {
            return (
              <div
                key={index}
                className={`cursor-text relative flex justify-center items-center w-20 h-20 outline outline-1 outline-primary-400 text-4xl text-center `}
              />
            )
          })}
        </div>
      </div>
    </div>
  )
}

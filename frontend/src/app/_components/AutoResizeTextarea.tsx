'use client'

import { useFormContext } from 'react-hook-form'

interface AutoResizeTextareaProps {
  fieldName: string
  placeholder: string
  className: string
}

const AutoResizeTextarea = ({
  fieldName,
  placeholder = '',
  className = '',
}: AutoResizeTextareaProps) => {
  const { register } = useFormContext()
  const { ref, ...rest } = register(fieldName)
  const adjustHeight = (element: HTMLTextAreaElement) => {
    element.style.height = '0'
    element.style.height = `${element.scrollHeight}px`
  }

  return (
    <textarea
      ref={(element) => {
        ref(element)
        if (element) {
          adjustHeight(element)
        }
      }}
      {...rest}
      placeholder={placeholder}
      className={`overflow-hidden resize-none outline-none ${className}`}
      onInput={(e) => adjustHeight(e.currentTarget)}
    />
  )
}

export default AutoResizeTextarea

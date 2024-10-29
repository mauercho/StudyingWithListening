interface ConfirmCancelModal {
  content: string
  buttonLabel: string
}

export default function ConfirmCancelModal({
  content,
  buttonLabel,
}: ConfirmCancelModal) {
  return (
    <div className="w-[30rem] max-h-80 bg-white rounded-3xl flex flex-col justify-between items-center gap-10 px-8 py-10 text-xl">
      <p className="text-gray-900">{content}</p>
      <div className="flex gap-4 w-full">
        <button className="submit-btn bg-gray-300 w-full">취소</button>
        <button className="submit-btn bg-primary-400 w-full whitespace-nowrap">
          {buttonLabel}
        </button>
      </div>
    </div>
  )
}

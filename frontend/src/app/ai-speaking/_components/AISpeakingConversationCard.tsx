import Image from 'next/image'

interface AISpeakingConversationCardProps {
  conversationTitle: string
  conversationDescription: string
  conversationCoverImg: string
}

export default function AISpeakingConversationCard({
  conversationTitle,
  conversationDescription,
  conversationCoverImg,
}: AISpeakingConversationCardProps) {
  return (
    <div className="w-80 h-[28rem] rounded-3xl flex flex-col bg-white overflow-hidden text-gray-900 shadow-md">
      <div className="h-3/5">
        <Image
          src={conversationCoverImg}
          width={400}
          height={400}
          alt={conversationTitle}
          className="h-full object-cover"
          priority
        />
      </div>
      <div className="p-4 flex-1 flex flex-col justify-center items-center text-center gap-6">
        <h2 className="font-medium">{conversationTitle}</h2>
        <p className="text-sm">{conversationDescription}</p>
      </div>
    </div>
  )
}

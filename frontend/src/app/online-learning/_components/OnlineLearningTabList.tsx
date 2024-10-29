'use client'

import ChatIcon from '/public/icons/chat.svg'
import NoteIcon from '/public/icons/notebook.svg'
import WriteIcon from '/public/icons/edit.svg'

export type TabId = 'lecture-note' | 'lecture-handout' | 'lecture-chat' | 'none'

interface Tab {
  id: TabId
  label: string
  icon: React.ReactNode
  description: string
}

export const tabList: Tab[] = [
  {
    id: 'lecture-note',
    label: '강의노트',
    icon: <WriteIcon />,
    description: '수업을 들으면서 중요한 부분을 기록해요!',
  },
  {
    id: 'lecture-handout',
    label: '수업자료',
    icon: <NoteIcon />,
    description: '수업자료 중 복습하고 싶은 문장을 저장하세요!',
  },
  {
    id: 'lecture-chat',
    label: '채팅',
    icon: <ChatIcon />,
    description: '수업과 관련 없는 내용은 피해주세요!',
  },
]

interface OnlineLearningTabListProps {
  activeTab: TabId
  setActiveTab: React.Dispatch<React.SetStateAction<TabId>>
  setIsOpenSideBar: React.Dispatch<React.SetStateAction<boolean>>
}

export default function OnlineLearningTabList({
  activeTab,
  setActiveTab,
  setIsOpenSideBar,
}: OnlineLearningTabListProps) {
  return (
    <div className="py-6 z-10 fixed right-0 w-20 h-screen bg-white flex flex-col items-center gap-2">
      {tabList.map((tab) => (
        <button
          onClick={() => {
            if (tab.id === activeTab) {
              setActiveTab('none')
              setIsOpenSideBar(false)
            } else {
              setActiveTab(tab.id)
              setIsOpenSideBar(true)
            }
          }}
          key={tab.id}
          className={`flex flex-col items-center gap-1 w-full py-2 text-primary-400 hover:font-medium ${activeTab === tab.id && 'bg-primary-400 text-white'}`}
        >
          <div className="w-8">{tab.icon}</div>
          <p className="font-medium">{tab.label}</p>
        </button>
      ))}
    </div>
  )
}

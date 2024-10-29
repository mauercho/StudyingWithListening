'use client'

import { ComponentType, useState } from 'react'
import OnlineLearningPanel from './OnlineLearningPanel'
import OnlineLearningTabList, { TabId } from './OnlineLearningTabList'
import { AnimatePresence } from 'framer-motion'
import OnlineLearningLectureNotePanel from './OnlineLearningLectureNotePanel'
import OnlineLearningHandoutPanel from './OnlineLearningHandoutPanel'
import OnlineLearningChatPanel from './OnlineLearningChatPanel'

const renderPanel = (activeTab: TabId): React.ReactNode => {
  switch (activeTab) {
    case 'lecture-note':
      return <OnlineLearningLectureNotePanel />
    case 'lecture-handout':
      return <OnlineLearningHandoutPanel />
    case 'lecture-chat':
      return <OnlineLearningChatPanel />
    default:
      return null
  }
}

interface OnlineLearningSideBarProps {
  setIsOpenSideBar: React.Dispatch<React.SetStateAction<boolean>>
}

export default function OnlineLearningSideBar({
  setIsOpenSideBar,
}: OnlineLearningSideBarProps) {
  const [activeTab, setActiveTab] = useState<TabId>('none')
  return (
    <div className="fixed top-0 z-[60] max-w-md h-screen overflow-clip">
      <OnlineLearningTabList
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        setIsOpenSideBar={setIsOpenSideBar}
      />
      <AnimatePresence initial={false} mode={'wait'}>
        {activeTab !== 'none' && (
          <OnlineLearningPanel key={activeTab} activeTab={activeTab}>
            {renderPanel(activeTab)}
          </OnlineLearningPanel>
        )}
      </AnimatePresence>
    </div>
  )
}

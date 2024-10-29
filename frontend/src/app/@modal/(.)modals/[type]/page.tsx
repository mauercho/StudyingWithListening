'use client'

import AddSentenceModal from '@/app/modals/_components/AddSentenceModal'
import BasicModal from '@/app/modals/_components/BasicModal'
import ConfirmCancelModal from '@/app/modals/_components/ConfirmCancelModal'
import DictationModal from '@/app/modals/_components/DictationModal'
import EditNicknameModal from '@/app/modals/_components/EditNicknameModal'
import { ModalLayout } from '@/app/modals/_components/ModalLayout'

export default function ModalPage({ params }: { params: { type: string } }) {
  const renderModal = () => {
    switch (params.type) {
      case 'basic':
        return <BasicModal />
      case 'confirm-cancel':
        return (
          <ConfirmCancelModal
            content="정말 탈퇴할거임?"
            buttonLabel="탈퇴하기"
          />
        )
      case 'dictation-category':
        return <DictationModal />
      case 'add-sentence':
        return <AddSentenceModal />
      case 'edit-nickname':
        return <EditNicknameModal />
    }
  }

  return <ModalLayout>{renderModal()}</ModalLayout>
}

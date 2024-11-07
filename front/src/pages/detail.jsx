import React, { useState } from 'react'

import { useParams } from 'react-router-dom'
import styled from '@emotion/styled'
import { scroller, Element } from 'react-scroll'

import TableOfContents from '../components/TableOfContents'
import Sentence from '../components/Sentence'
import Modal from '../components/Modal'

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  width: 100%;
  height: 85vh;
`

const HeaderWrapper = styled.div`
  width: 100%;
  max-width: 768px;
  background: ${({ theme }) => theme.color.white};
  z-index: 80;
  border-radius: 0 0 16px 16px;
`

const ContentArea = styled.ul`
  flex: 1;
  width: 100%;
  max-width: 768px;
  padding: 10px;
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  overflow-y: auto;
  box-sizing: border-box;
  background: ${({ theme }) => theme.color.grey};
`

const indexes = [
  { indexId: 1, indexTitle: '컴퓨터와 이진수', sentenceId: 1 },
  { indexId: 2, indexTitle: '알고리즘이란?', sentenceId: 2 },
]

const sentences = [
  {
    id: 1,
    text: '컴퓨터는 이진수로 데이터를 표현하며, 0과 1로 이루어진 비트를 사용합니다.',
    status: 'normal',
  },
  {
    id: 2,
    text: '알고리즘은 문제 해결을 위한 명확한 절차 또는 단계입니다.',
    status: 'normal',
  },
]

export default function Detail({ summaryId }) {
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [modalFlag, setModalFlag] = useState(false)
  const [isTableOpen, setIsTableOpen] = useState(false) // 목차 열림 상태 추가

  const handleShortPress = (sentenceId, status) => {
    if (status === 'hidden') {
      setModalFlag(false)
      setTimeout(() => setIsModalOpen(true), 50)
    }
    console.log(`Sentence ${sentenceId}`)
  }

  const handleLongPress = (sentenceId, status) => {
    setModalFlag(status === 'hidden' ? false : true)
    setIsModalOpen(true)
    console.log(`Sentence ${sentenceId}`)
  }

  const handleTableTouch = (sentenceId) => {
    scroller.scrollTo(`sentence-${sentenceId}`, {
      containerId: 'content-area',
      duration: 500,
      delay: 0,
      smooth: 'easeInOutQuart',
      offset: -20,
    })
  }

  const toggleTable = () => {
    setIsTableOpen((prev) => !prev)
  }

  const closeModal = () => {
    setIsModalOpen(false)
  }

  return (
    <Container>
      <HeaderWrapper>
        <TableOfContents
          indexes={indexes}
          onButtonClick={handleTableTouch}
          isOpen={isTableOpen}
          toggleOpen={toggleTable}
        />
      </HeaderWrapper>
      <ContentArea id="content-area">
        {sentences.map((sentence) => (
          <Element name={`sentence-${sentence.id}`} key={sentence.id}>
            <Sentence
              text={sentence.text}
              status={sentence.status}
              onShortPress={() =>
                handleShortPress(sentence.id, sentence.status)
              }
              onLongPress={() => handleLongPress(sentence.id, sentence.status)}
            />
          </Element>
        ))}
      </ContentArea>
      <Modal isOpen={isModalOpen} onClose={closeModal} flag={modalFlag} />
    </Container>
  )
}

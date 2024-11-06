import React, { useState } from 'react'
import styled from '@emotion/styled'

import TableOfContents from '../components/TableOfContents'
import Sentence from '../components/Sentence'
import Modal from '../components/Modal'

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`

const Header = styled.div`
  position: fixed;
  top: 60px;
  width: 100%;
  background: ${({ theme }) => theme.color.white};
  z-index: 80;
  box-sizing: border-box;
  border-radius: 0 0 16px 16px;
`

const ContentArea = styled.ul`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  padding: 10px;
  padding-top: 34px;
  gap: 10px;
  overflow-x: hidden;
  box-sizing: border-box;
  background: ${({ theme }) => theme.color.grey};
`

const indexes = [
  { indexId: 1, indexTitle: '컴퓨터와 이진수', sentenceId: 1 },
  { indexId: 2, indexTitle: '알고리즘이란?', sentenceId: 2 },
  { indexId: 3, indexTitle: '빅오 표기법의 중요성', sentenceId: 3 },
  { indexId: 4, indexTitle: '스택 구조의 개념', sentenceId: 4 },
  { indexId: 5, indexTitle: '큐 구조와 FIFO', sentenceId: 5 },
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
  {
    id: 3,
    text: '빅오 표기법은 알고리즘의 효율성을 분석하기 위한 방법입니다.',
    status: 'hidden',
  },
  {
    id: 4,
    text: '스택은 LIFO(Last In, First Out) 방식으로 동작하는 데이터 구조입니다.',
    status: 'normal',
  },
  {
    id: 5,
    text: '큐는 FIFO(First In, First Out) 방식으로 데이터를 처리합니다.',
    status: 'playing',
  },
]

export default function Detail() {
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [modalFlag, setModalFlag] = useState(false)

  const handleShortPress = (id, status) => {
    if (status === 'hidden') {
      setModalFlag(false)
      setTimeout(() => setIsModalOpen(true), 50)
    }
    console.log(`Sentence ${id}`)
  }

  const handleLongPress = (id, status) => {
    setModalFlag(status === 'hidden' ? false : true)
    setIsModalOpen(true)
    console.log(`Sentence ${id}`)
  }

  const closeModal = () => {
    setIsModalOpen(false)
  }

  return (
    <Container>
      <Header>
        <TableOfContents indexes={indexes} />
      </Header>
      <ContentArea>
        {sentences.map((sentence) => (
          <Sentence
            key={sentence.id}
            text={sentence.text}
            status={sentence.status}
            onShortPress={() => handleShortPress(sentence.id, sentence.status)}
            onLongPress={() => handleLongPress(sentence.id, sentence.status)}
          />
        ))}
      </ContentArea>
      <Modal isOpen={isModalOpen} onClose={closeModal} flag={modalFlag} />
    </Container>
  )
}

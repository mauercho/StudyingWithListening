import React from 'react'

import styled from '@emotion/styled'
import { MdOutlineClose } from 'react-icons/md'

import helpSummary from '../assets/images/tutorial/bookmarks_help.svg'
import helpQuestion from '../assets/images/tutorial/sentences_help.svg'
import helpStyle from '../assets/images/tutorial/summary_help.svg'
import theme from '../assets/styles/theme'

const ModalBackground = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  backdrop-filter: blur(5px);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 100;
`
const ModalHeader = styled.div`
  margin: 0;
  display: flex;
  align-items: center;

  justify-content: flex-end;
`

const ModalContainer = styled.div`
  background-color: ${theme.color.white};
  width: 80%;
  max-width: 400px;
  max-height: 80vh;
  border-radius: 20px;
  padding: 10px;
  overflow-y: auto;
  position: relative;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
`

const CloseButton = styled.button`
  background: none;
  border: none;
  padding: 0;
`

const Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: 30px;
  padding: 10px;
`

const Section = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
  border-bottom: 1px solid ${theme.color.primary_dark};
`

const Text = styled.p`
  font-size: 17px;
  line-height: 22px;
  font-weight: ${theme.font.weight.regular};
  margin: 0;
`

const Image = styled.img`
  width: 100%;
  height: auto;

  ${({ isSummary }) =>
    isSummary &&
    `
    width: 70%;  // 원하는 비율로 조절
    margin: 0 auto;  // 가운데 정렬
  `}

  ${({ isStyle }) =>
    isStyle &&
    `
    width: 90%;  // 원하는 비율로 조절
    margin: 0 auto;  // 가운데 정렬
  `}
`

const helpContents = [
  {
    text: '요약문을 클릭해 해당 부분을 재생할 수 있어요.',
    image: helpQuestion,
  },
  {
    text: '원하는 스타일의 요약문을 선택해서 볼 수 있어요.',
    image: helpSummary,
  },
  {
    text: '요약내용이 마음에 들지 않으면 문장을 꾹 눌러 수정 요청을 보낼 수 있어요.',
    image: helpStyle,
  },
]

export default function HelpModal({ isOpen, onClose }) {
  if (!isOpen) return null

  return (
    <ModalBackground onClick={onClose}>
      <ModalContainer onClick={(e) => e.stopPropagation()}>
        <ModalHeader>
          <CloseButton onClick={onClose}>
            <MdOutlineClose size={24} />
          </CloseButton>
        </ModalHeader>
        <Content>
          {helpContents.map((content, index) => (
            <Section key={index}>
              <Text>{content.text}</Text>
              <Image
                src={content.image}
                alt={`도움말 이미지 ${index + 1}`}
                isSummary={content.image === helpSummary}
                isStyle={content.image === helpStyle}
              />
            </Section>
          ))}
        </Content>
      </ModalContainer>
    </ModalBackground>
  )
}

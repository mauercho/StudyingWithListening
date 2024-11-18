import React from 'react'

import styled from '@emotion/styled'
import { MdOutlineClose } from 'react-icons/md'

import helpModeSelect from '../assets/images/tutorial/helpModeSelect.svg'
import helpTouchMusic from '../assets/images/tutorial/helpTouchMusic.svg'
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

  justify-content: space-between;
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
  gap: 10px;
  padding: 10px;
`

const Section = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
  border-bottom: 1px solid ${theme.color.primary_dark};
`

const Title = styled.p`
  font-size: ${theme.font.size.lg};
  font-weight: ${theme.font.weight.medium};
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

  ${({ isMusic }) =>
    isMusic &&
    `
    width: 70%;  // 원하는 비율로 조절
    margin: 0 auto;  // 가운데 정렬
  `}

  ${({ isMode }) =>
    isMode &&
    `
    width: 90%;  // 원하는 비율로 조절
    margin: 0 auto;  // 가운데 정렬
  `}
`

const helpContents = [
  {
    text: '요약문을 클릭해 해당 부분을 재생할 수 있어요.',
    image: helpTouchMusic,
  },
  {
    text: '상황에 맞는 듣기 방식을 선택해 보세요!',
    image: helpModeSelect,
  },
]

const Footer = styled.div`
  padding: 10px;

  p {
    opacity: 0.3;
    font-size: ${theme.font.size.base};
    font-weight: ${theme.font.weight.regular};
    overflow-wrap: break-word;
  }
`

const Button = styled.button`
  padding: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  color: white;
  background: ${theme.color.primary_dark};
  font-size: ${theme.font.size.base};
  font-weight: ${theme.font.weight.regular};
  border-radius: 10px;
  border: 0;
`

export default function HelpModal({ isOpen, onClose, onButtonClick }) {
  if (!isOpen) return null

  return (
    <ModalBackground onClick={onClose}>
      <ModalContainer onClick={(e) => e.stopPropagation()}>
        <ModalHeader>
          <Title>요약 페이지 안내</Title>

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
                isMusic={content.image === helpTouchMusic}
                isMode={content.image === helpModeSelect}
              />
              {index === 1 && (
                <Button onClick={onButtonClick}>요약 모드 선택하러 가기</Button>
              )}
            </Section>
          ))}
        </Content>
        <Footer>
          <p>
            모드는 상세페이지 우측 상단 버튼을 통해 언제든지 변경하실 수
            있습니다.
          </p>
        </Footer>
      </ModalContainer>
    </ModalBackground>
  )
}

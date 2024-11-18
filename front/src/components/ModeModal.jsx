import React from 'react'
import styled from '@emotion/styled'
import { MdOutlineClose } from 'react-icons/md'
import Lottie from 'react-lottie'

import animationData1 from '../assets/lottie/associativeMemory.json'
import animationData2 from '../assets/lottie/running.json'
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
  opacity: 0;
  transform: scale(0.9);
  animation: fadeIn 0.3s ease forwards;

  @keyframes fadeIn {
    0% {
      opacity: 0;
      transform: scale(0.9);
    }
    100% {
      opacity: 1;
      transform: scale(1);
    }
  }
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

const ModalHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`

const Title = styled.p`
  font-size: ${theme.font.size.lg};
  font-weight: ${theme.font.weight.medium};
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

const ContentSet = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: space-between;
  border-bottom: 1px solid ${theme.color.primary_dark};
`

const ButtonSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 10px;
`

const SectionText = styled.p`
  font-size: ${theme.font.size.lg};
  font-weight: ${theme.font.weight.regular};
  padding: 10px;
  width: 100%;
`

const Button = styled.button`
  padding: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  color: white;
  font-size: ${theme.font.size.base};
  font-weight: ${theme.font.weight.regular};
  border-radius: 10px;
  border: 0;
`

const Footer = styled.div`
  padding: 10px;

  p {
    opacity: 0.3;
    font-size: ${theme.font.size.base};
    font-weight: ${theme.font.weight.regular};
    overflow-wrap: break-word;
  }
`

export default function ModeModal({ isOpen, onClose, onSelectMode }) {
  if (!isOpen) return null

  const thinkImageOptions = {
    loop: true,
    autoplay: true,
    animationData: animationData1,
    rendererSettings: {
      preserveAspectRatio: 'xMidYMid slice',
    },
  }

  const runningImageOptions = {
    loop: true,
    autoplay: true,
    animationData: animationData2,
    rendererSettings: {
      preserveAspectRatio: 'xMidYMid slice',
    },
  }

  const handleModeSelect = (mode) => {
    onSelectMode(mode)
  }

  return (
    <ModalBackground onClick={onClose}>
      <ModalContainer onClick={(e) => e.stopPropagation()}>
        <ModalHeader>
          <Title>요약 모드 선택</Title>
          <CloseButton onClick={onClose}>
            <MdOutlineClose size={24} />
          </CloseButton>
        </ModalHeader>
        <Content>
          <ContentSet>
            <Lottie options={thinkImageOptions} height={90} width={90} />
            <ButtonSection>
              <SectionText>연상 암기를 통해 쉽게 외워보세요!</SectionText>
              <Button
                onClick={() => handleModeSelect('detail')}
                style={{ backgroundColor: theme.color.primary_dark }}
              >
                연상 암기 모드 선택
              </Button>
            </ButtonSection>
          </ContentSet>
          <ContentSet>
            <Lottie options={runningImageOptions} height={90} width={90} />
            <ButtonSection>
              <SectionText>시험 5분 전, 키워드만 쏙쏙 들려드려요.</SectionText>
              <Button
                onClick={() => handleModeSelect('simple')}
                style={{ backgroundColor: theme.color.secondary }}
              >
                시험 5분전 모드 선택
              </Button>
            </ButtonSection>
          </ContentSet>
          <Footer>
            <p>
              모드는 상세페이지 우측 상단 버튼을 통해 언제든지 변경하실 수
              있습니다.
            </p>
          </Footer>
        </Content>
      </ModalContainer>
    </ModalBackground>
  )
}

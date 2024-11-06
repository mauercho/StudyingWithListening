import React from 'react'

import styled from '@emotion/styled'
import { MdOutlineClose } from 'react-icons/md'

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

const ModalContainer = styled.div`
  background-color: ${theme.color.white};
  width: 80%;
  max-width: 320px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
`

const ModalHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`

const Title = styled.h2`
  font-size: ${theme.font.size.lg};
  font-weight: ${theme.font.weight.bold};
  margin: 0;
`

const CloseButton = styled.button`
  background: none;
  border: none;
  font-size: 20px;
  padding: 0;
  height: 20px;
`

const Content = styled.div`
  font-size: 14px;
  line-height: 1.5;
  display: flex;
  flex-direction: column;
  gap: 10px;
`

const Paragraph = styled.p`
  padding: 10px;
  background: ${theme.color.grey};
  borrder-radius: 10px;
  border: none;
  font-weight: ${theme.font.weight.regular};
  font-size: ${theme.font.size.base};
  &:active {
    background: ${theme.color.grey_dark};
`

const ButtonContainer = styled.div`
  display: flex;
  gap: 10px;
`

const Button = styled.button`
  flex: 1;
  padding: 10px;
  border: none;
  border-radius: 10px;
  height: 44px;
  font-size: ${theme.font.weight.bold};
  font-size: ${theme.font.size.base};
  font-family: 'TheJamsil', sans-serif;
  cursor: pointer;

  ${(props) =>
    props.primary
      ? `
    background-color: ${theme.color.primary_dark};
    color: ${theme.color.white};
    &:active {
    background-color: #3A6F59;}
  `
      : `
    background-color: ${theme.color.accent};
    color:  ${theme.color.white};
    &:active {
    background-color: #D14545;;
  `}
`

const Modal = ({ isOpen, onClose, flag = true }) => {
  if (!isOpen) return null

  return (
    <ModalBackground onClick={onClose}>
      <ModalContainer onClick={(e) => e.stopPropagation()}>
        <ModalHeader>
          <Title>{flag ? '어떤 내용이 필요하세요?' : '숨긴 내용이에요.'}</Title>
          <CloseButton onClick={onClose}>
            <MdOutlineClose />
          </CloseButton>
        </ModalHeader>
        {flag && (
          <Content>
            <Paragraph onClick={onClose}>
              내용이 이상해. 다시 요약해 줘.
            </Paragraph>
            <Paragraph onClick={onClose}>더 자세하게 설명해 줘.</Paragraph>
          </Content>
        )}
        <ButtonContainer>
          <Button primary onClick={onClose}>
            {flag ? '숨기기' : '숨김 해제'}
          </Button>
          <Button onClick={onClose}>내용 삭제</Button>
        </ButtonContainer>
      </ModalContainer>
    </ModalBackground>
  )
}

export default Modal

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

const ModalHeader = styled.div`
  margin: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-bottom: 10px;
`

const ModalContainer = styled.div`
  background-color: ${theme.color.white};
  width: 90%; /* 너비를 더 넓게 */
  max-width: 768px; /* 최대 너비 확대 */
  max-height: 90vh; /* 최대 높이 확대 */
  min-height: 70vh;
  height: auto; /* 내용에 따라 높이 조정 */
  border-radius: 20px;
  padding: 10px;
  overflow: hidden; /* 스크롤바가 생기지 않도록 */
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
  justify-content: center;
  align-items: center;
  width: 100%;
  height: calc(100% - 40px);
  overflow: hidden;
`

const Iframe = styled.iframe`
  width: 100%;
  height: 100%;
  border: none;
`

const Title = styled.p`
  font-size: ${theme.font.size.lg};
  font-weight: ${theme.font.weight.medium};
`

export default function ContentsModal({ isOpen, onClose, url }) {
  if (!isOpen) return null

  const isImage =
    url?.includes('.jpg') ||
    url?.includes('.jpeg') ||
    url?.includes('.png') ||
    url?.includes('.gif') ||
    url?.includes('.webp')
  const isPDF = url?.includes('.pdf')
  console.log('Image : ', isImage, 'PDF :', isPDF)

  return (
    <ModalBackground onClick={onClose}>
      <ModalContainer onClick={(e) => e.stopPropagation()}>
        <ModalHeader>
          <Title>원본 파일 보기</Title>
          <CloseButton onClick={onClose}>
            <MdOutlineClose size={24} />
          </CloseButton>
        </ModalHeader>
        <Content>
          {isImage && (
            <img
              src={url}
              alt="Uploaded content"
              style={{ maxWidth: '100%', maxHeight: '100%' }}
            />
          )}
          {isPDF && <Iframe src={url} title="PDF Viewer" />}
          {!isImage && !isPDF && <p>키워드로 생성되어서 원본이 없어요.</p>}
        </Content>
      </ModalContainer>
    </ModalBackground>
  )
}

import React, { useEffect, useState } from 'react'
import styled from '@emotion/styled'
import { FaCheck, FaCircleNotch } from 'react-icons/fa6'
import ProgressBar from './home/ProgressBar'
import summariesApi from '../api/summariesApi'

const Spinner = styled(FaCircleNotch)`
  animation: spin 1s linear infinite;
  @keyframes spin {
    0% {
      transform: rotate(0deg);
    }
    100% {
      transform: rotate(360deg);
    }
  }
`

const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
`

const ModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 300px;
  justify-content: center;
  align-items: center;
  padding: 10px;
  gap: 10px;
  border-radius: 8px;
  border: 1px solid ${({ theme }) => theme.color.primary};
  background: ${({ theme }) => theme.color.primary_light};
`

const ContentBase = styled.div`
  display: flex;
  height: 18rem;
  flex-direction: column;
  width: 100%;
  border-radius: 4px;
  border: 1px dashed ${({ theme }) => theme.color.primary_dark};
  background: ${({ theme }) => theme.color.white};
  gap: 10px;
`

const UploadContent = styled(ContentBase)`
  align-items: center;
  justify-content: center;

  & > div {
    color: ${({ theme }) => theme.color.primary};
    text-align: center;
  }
`

const SchemeContent = styled(ContentBase)`
  padding: 10px;
  width: calc(100% - 20px);
  color: ${({ theme }) => theme.color.primary_dark};
  display: flex;
  overflow-y: scroll;
  flex-direction: column;
  gap: 20px;
`

const TitleInput = styled.input`
  width: calc(100% - 20px);
  height: 40px;
  border: none;
  padding: 0px 10px;

  &:focus {
    outline: none;
  }
`

const Button = styled.button`
  border-radius: 16px;
  background: ${({ theme }) => theme.color.primary};
  width: 10rem;
  height: 2rem;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  color: ${({ theme }) => theme.color.white};
  cursor: pointer;
  font-weight: ${({ theme }) => theme.font.weight.medium};

  &:disabled {
    opacity: 0.4;
  }
`

const CloseButton = styled.button`
  background: none;
  border: none;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  align-self: flex-end;
  color: ${({ theme }) => theme.color.primary_dark};

  &:hover {
    color: ${({ theme }) => theme.color.primary};
  }
`

export default function WordModal({ isOpen, onClose, text, ...props }) {
  const initialState = {
    serverResponse: false,
    title: '',
    qList: [
      { content: 'DBMS는 어떻게 데이터 무결성을 유지하나요?' },
      { content: 'DBMS는 어떻게 데이터 무결성을 유지하나요?' },
      { content: 'DBMS는 어떻게 데이터 무결성을 유지하나요?' },
      { content: 'DBMS는 어떻게 데이터 무결성을 유지하나요?' },
      { content: 'DBMS는 어떻게 데이터 무결성을 유지하나요?' },
    ],
  }

  const [state, setState] = useState(initialState)

  const handleComplete = () => {
    setState(initialState) // 상태를 초기값으로 되돌림
    onClose() // 모달 닫기
  }

  // 모달이 열릴 때만 요청 실행
  useEffect(() => {
    if (isOpen) {
      handleUpload();
    }
  }, [isOpen]); // isOpen 값이 변경될 때 실행

  const handleUpload = async () => {
    const formData = new FormData()
    formData.append('type', 'keyword')
    formData.append('keyword', text)
    try {
      const result = await summariesApi.postSummaries(formData)
    } catch (error) {
      console.error('Upload failed:', error)
    }
    setState((prev) => ({ ...prev, serverResponse: true }))
  }

  if (!isOpen) return null

  return (
    <Overlay>
      <ModalContainer>
        <CloseButton
          onClick={() => {
            setState(initialState) // 초기화 후 모달 닫기
            onClose()
          }}
        >
          &times;
        </CloseButton>
        <div>
          학습할 내용이에요. 본격적인 듣기 학습 전에 배울 내용을 읽어보면 학습
          효과가 올라간답니다!
        </div>
        <SchemeContent>
          {state.qList.map((v, idx) => (
            <p key={idx}>
              {idx + 1}. Q: {v.content || '질문이 들어갑니다?'}
            </p>
          ))}
        </SchemeContent>
        <TitleInput
          placeholder="제목을 입력하세요"
          value={state.title}
          onChange={(e) =>
            setState((prev) => ({ ...prev, title: e.target.value }))
          }
        />
        <ProgressBar serverResponse={state.serverResponse} />
        {state.serverResponse && state.title !== '' ? (
          <Button onClick={handleComplete}>
            <FaCheck size={18} />
            <span>완료</span>
          </Button>
        ) : (
          <Button>
            <Spinner size={16} />
          </Button>
        )}
      </ModalContainer>
    </Overlay>
  )
}
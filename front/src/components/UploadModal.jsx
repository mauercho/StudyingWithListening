import React, { useState, useRef } from 'react'
import styled from '@emotion/styled'
import {
  FaArrowUp,
  FaUpload,
  FaCheck,
  FaCircleNotch,
  FaLightbulb,
  FaBookOpen,
} from 'react-icons/fa6'
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

const SchemeTitle = styled.div`
  width: 100%;
  div {
    display: flex;
    align-items: center;
    margin-top: 5px;

    span {
      margin-right: 4px;
    }

    &:first-of-type {
      color: ${({ theme }) => theme.color.black};
      span {
        color: ${({theme}) => theme.color.primary};
      }
    }

    &:last-of-type {
      color: #dc143c;
      span {
        color: #ffd400;
      }
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

export default function UploadModal({ isOpen, onClose, direct = false }) {
  let sse

  const handleConnectInit = () => {
    sse = new EventSource('https://k11a304.p.ssafy.io/api/alert/connect')

    // 연결되었을 때 이벤트리스너
    // receivedConnectData : "connected"
    sse.addEventListener('connect', (e) => {
      const { data: receivedConnectData } = e
      console.log('connect event data: ', receivedConnectData)
    })

    // question들이 모두 완성되었을 때 이벤트리스너
    // receivedConnectData 예시 : {
    //      summaryId : int
    //      questions: Array(String)
    // }
    sse.addEventListener('all questions of summary are created', (e) => {
      const { data: receivedConnectData } = e
      console.log('question created event data: ', receivedConnectData)
    })

    // answer들이 모두 완성되었을 때 이벤트리스너
    // receivedConnectData : summaryId
    sse.addEventListener('all answers of summary are created', (e) => {
      const { data: receivedConnectData } = e
      console.log('answer created event data: ', receivedConnectData)
    })
  }

  const initialState = {
    uploadStatus: direct,
    serverResponse: false,
    data: '',
    name: '',
    title: '',
    qList: [
      // { content: 'DBMS는 어떻게 데이터 무결성을 유지하나요?' },
      // { content: 'DBMS는 어떻게 데이터 무결성을 유지하나요?' },
      // { content: 'DBMS는 어떻게 데이터 무결성을 유지하나요?' },
      // { content: 'DBMS는 어떻게 데이터 무결성을 유지하나요?' },
      // { content: 'DBMS는 어떻게 데이터 무결성을 유지하나요?' },
    ],
  }

  const [state, setState] = useState(initialState)
  const inputRef = useRef(null)

  const handleFileChange = (event) => {
    const file = event.target.files[0]
    if (file) {
      setState((prev) => ({ ...prev, name: file.name, data: file }))
    }
  }

  const handleComplete = () => {
    setState(initialState) // 상태를 초기값으로 되돌림
    onClose() // 모달 닫기
  }

  const handleUpload = async () => {
    if (state.data === '') {
      alert('학습자료가 없습니다!')
      return
    }
    handleConnectInit()
    setState((prev) => ({ ...prev, uploadStatus: true }))

    const formData = new FormData()
    formData.append('type', 'image')
    formData.append('contentFile', state.data)

    try {
      const result = await summariesApi.postSummaries(formData)
      setState((prev) => ({ ...prev, serverResponse: true }))
    } catch (error) {
      console.error('Upload failed:', error)
    }
  }

  const handleUploadFile = () => {
    inputRef.current.accept = 'application/pdf,image/*'
    inputRef.current.click()
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
        {state.uploadStatus && (
          <SchemeTitle>
            <div>
              <span>
                <FaBookOpen />
              </span>
              배울 내용을 미리 읽어보세요.
            </div>
            <div>
              <span>
                <FaLightbulb />
              </span>
              듣기 학습의 효과가 더욱 높아져요!
            </div>
          </SchemeTitle>
        )}
        {state.uploadStatus ? (
          <SchemeContent>
            {state.qList.map((v, idx) => (
              <p key={idx}>
                {idx + 1}. Q: {v.content || '질문이 들어갑니다?'}
              </p>
            ))}
          </SchemeContent>
        ) : (
          <UploadContent onClick={handleUploadFile}>
            {state.name ? (
              <div>{state.name}</div>
            ) : (
              <div>
                <FaUpload size={32} />
                <p style={{marginTop: '8px', color: "black"}}>눌러서 학습자료를 업로드 해보세요!</p>
              </div>
            )}
            <input
              type="file"
              ref={inputRef}
              style={{ display: 'none' }}
              onChange={handleFileChange}
            />
          </UploadContent>
        )}
        {state.uploadStatus && (
          <TitleInput
            placeholder="제목을 입력하세요"
            value={state.title}
            onChange={(e) =>
              setState((prev) => ({ ...prev, title: e.target.value }))
            }
          />
        )}
        {state.uploadStatus && (
          <ProgressBar serverResponse={state.serverResponse} />
        )}
        {!state.uploadStatus ? (
          <Button onClick={handleUpload} disabled={!state.data}>
            <FaArrowUp size={18} />
            <span>업로드 하기</span>
          </Button>
        ) : state.serverResponse && state.title !== '' ? (
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

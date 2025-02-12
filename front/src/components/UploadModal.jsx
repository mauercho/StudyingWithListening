import React, { useState, useRef } from 'react'
import { useNavigate } from 'react-router-dom'
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
        color: ${({ theme }) => theme.color.primary};
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
  animation: fadeIn 0.5s ease-out;
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
  border: 2px dashed ${({ theme }) => theme.color.primary_dark};
  transition: border-color 0.3s ease, background-color 0.3s ease;

  &:hover {
    background-color: ${({ theme }) => theme.color.grey_light};
    border-color: ${({ theme }) => theme.color.primary};
  }

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

  &::-webkit-scrollbar {
    width: 5px;
  }

  &::-webkit-scrollbar-thumb {
    background: ${({ theme }) => theme.color.primary};
    border-radius: 10px;
  }

  & > p {
    opacity: 0;
    transform: translateY(20px);
    animation: fadeInList 0.3s ease-in-out forwards;
  }

  @keyframes fadeInList {
    0% {
      opacity: 0;
      transform: translateY(20px);
    }
    100% {
      opacity: 1;
      transform: translateY(0);
    }
  }

  & > p:nth-of-type(1) {
    animation-delay: 0.1s;
  }

  & > p:nth-of-type(2) {
    animation-delay: 0.2s;
  }

  & > p:nth-of-type(3) {
    animation-delay: 0.3s;
  }
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
  const initialState = {
    uploadStatus: direct,
    serverResponse: false,
    data: '',
    name: '',
    title: '',
    qList: [],
  }

  const navigate = useNavigate() // navigate 인스턴스 생성
  const [state, setState] = useState(initialState)
  const [newSummaryId, setNewSummaryId] = useState(null)
  const inputRef = useRef(null)
  const titleRef = useRef(null)

  const handleFileChange = (event) => {
    const file = event.target.files[0]
    if (file) {
      setState((prev) => ({ ...prev, name: file.name, data: file }))
    }
  }

  const handleComplete = async () => {
    setState(initialState) // 상태 초기화

    // try {
    // const response = await summariesApi.patchSummaryTitle(
    //   newSummaryId,
    //   titleRef.current.value
    // )
    // 성공적으로 응답을 받았을 경우
    //  if (response?.status === 200) {
    //   console.log('Patch successful:', response.data)
    //   navigate(`/detail/${newSummaryId}`) // 성공 시 이동
    // } else {
    //   console.error('Unexpecected response:', response)
    //   navigate(`/detail/${newSummaryId}`) // 성공 시 이동
    // }
    summariesApi
      .patchSummaryTitle(newSummaryId, titleRef.current.value)
      .then(({ data }) => {
        console.log(data)
        navigate(`/detail/${newSummaryId}`)
      })
      .catch((err) => {
        console.error('Unexpecected response:', err)
      })
    // console.log(response)
    // console.log(response?.status)
    // } catch (error) {
    //   // 요청 실패 시 에러 로그 출력
    //   console.error('Patch failed:', error)
    // }
  }

  const handleUpload = async () => {
    if (state.data === '') {
      alert('학습자료가 없습니다!')
      return
    }
    setState((prev) => ({ ...prev, uploadStatus: true }))

    const formData = new FormData()
    formData.append('type', 'image')
    formData.append('contentFile', state.data)

    try {
      const result = await summariesApi.postSummaries(formData)
      console.log('Upload result:', result)

      // setState((prev) => ({ ...prev, serverResponse: true }))

      // 첫 번째 Polling 시작
      let isReady = false // 첫 번째 조건 충족 여부
      const maxRetries = 12 // 최대 12번 시도
      let attempts = 0

      while (!isReady && attempts < maxRetries) {
        try {
          const response = await summariesApi.getSummariesDetail(result) // API 호출
          console.log('Polling response:', response)

          if (response?.sentenceResponseList?.[0]) {
            console.log(
              'Sentence response is ready:',
              response.sentenceResponseList[0]
            )
            // `sentenceResponseList`에서 각 `question`을 추출하여 `questionList` 생성
            console.log(
              '이거%%%%%%%%%%%%%%%%%%%%%%%%',
              response.sentenceResponseList
            )
            const questionList = response.sentenceResponseList.map((item) => ({
              content: item.question,
            }))

            // `qList`에 저장
            setState((prev) => ({
              ...prev,
              qList: questionList,
            }))
            isReady = true
            break
          } else {
            console.log('Sentence response not ready yet...')
          }
        } catch (error) {
          console.error('Polling error:', error)
        }

        attempts++
        if (!isReady) {
          await new Promise((resolve) => setTimeout(resolve, 5000)) // 5초 대기
        }
      }

      if (!isReady) {
        console.warn('First polling reached maximum attempts.')
        return
      }

      // 두 번째 Polling 시작
      let allUrlsReady = false // 모든 URL 조건 충족 여부
      attempts = 0 // 재시도 횟수 초기화

      while (!allUrlsReady && attempts < maxRetries) {
        try {
          const response = await summariesApi.getSummariesDetail(result) // API 호출
          console.log('Polling response for URLs:', response)

          // 모든 sentenceResponseList의 normalAnswerVoiceUrl과 simpleAnswerVoiceUrl 확인
          if (
            response?.sentenceResponseList?.every(
              (sentence) =>
                sentence.normalAnswerVoiceUrl !== '' &&
                sentence.simpleAnswerVoiceUrl !== '' &&
                sentence.normalAnswer !== '' &&
                sentence.simpleAnswer
            )
          ) {
            console.log('All URLs are ready:', response.sentenceResponseList)
            setState((prev) => ({ ...prev, serverResponse: true }))
            allUrlsReady = true
            setNewSummaryId(result)
            break
          } else {
            console.log('URLs not ready yet...')
          }
        } catch (error) {
          console.error('Polling error for URLs:', error)
        }

        attempts++
        if (!allUrlsReady) {
          await new Promise((resolve) => setTimeout(resolve, 5000)) // 5초 대기
        }
      }

      if (!allUrlsReady) {
        console.warn('Second polling reached maximum attempts.')
      }
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
                Q{idx + 1}: {v.content || '질문이 들어갑니다?'}
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
                <p style={{ marginTop: '8px', color: 'black' }}>
                  눌러서 학습자료를 업로드 해보세요!
                </p>
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
            ref={titleRef}
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
        ) : state.serverResponse ? (
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

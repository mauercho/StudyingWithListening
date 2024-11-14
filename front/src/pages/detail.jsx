import React, { useState, useEffect } from 'react'

import { useParams } from 'react-router-dom'
import styled from '@emotion/styled'
import { scroller, Element } from 'react-scroll'

import TableOfContents from '../components/TableOfContents'
import Sentence from '../components/Sentence'
import Modal from '../components/Modal'
import sentencesApi from '../api/sentencesApi'
import summariesApi from '../api/summariesApi'
import Loading from '../components/Loading'
import usePlayerStore from '../stores/usePlayerStore'
import BookmarkMenu from '../components/BookmarkMenu'

const Container = styled.div`
  width: 100%;
  overflow-y: auto;
  box-sizing: border-box;
`

const HeaderWrapper = styled.div`
  width: 100%;
  max-width: 768px;
  top: 60px;
  position: fixed;
  background: ${({ theme }) => theme.color.white};
  z-index: 80;
  border-radius: 0 0 16px 16px;
  padding: 0 10px;
  box-sizing: border-box;
`

const ModeSelectWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row-reverse;
  top: 95px;
  position: fixed;
  padding: 0 10px;
  box-sizing: border-box;
`

const Main = styled.div`
  position: fixed;
  top: 120px;
  bottom: 75px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  padding: 0 10px;
  box-sizing: border-box;
`

const ContentArea = styled.ul`
  flex: 1;
  width: 100%;
  max-width: 768px;
  padding: 10px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  overflow-y: auto;
  box-sizing: border-box;
  background: ${({ theme }) => theme.color.grey};
`

export default function Detail() {
  const { summaryId } = useParams()
  const [indexes] = useState([
    // setIndexes 제거
    { indexId: 1, indexTitle: '컴퓨터와 이진수', sentenceId: 1 },
    { indexId: 2, indexTitle: '알고리즘이란?', sentenceId: 2 },
  ])

  const { setSummaryTitle, setVoiceUrls, setCurrentIndex, voiceUrls, reset } =
    usePlayerStore()

  const [sentences, setSentences] = useState([])

  useEffect(() => {
    const fetchSummaryDetail = async () => {
      try {
        const data = await summariesApi.getSummariesDetail(summaryId)
        setSentences(data.sentenceResponseList)

        // 현재 페이지의 URL들
        const newUrls = data.sentenceResponseList.map(
          (sentence) => sentence.voiceUrl
        )

        // 완전히 새로운 페이지인 경우에만 초기화
        if (voiceUrls.length === 0) {
          setSummaryTitle(data.summaryTitle)
          setVoiceUrls(newUrls)
        }
        // 다른 디테일 페이지로 이동한 경우
        else if (!newUrls.some((url) => voiceUrls.includes(url))) {
          reset()
          setSummaryTitle(data.summaryTitle)
          setVoiceUrls(newUrls)
        }
        // 같은 페이지인 경우 제목만 업데이트
        else {
          setSummaryTitle(data.summaryTitle)
        }
      } catch (error) {
        console.error('Error:', error)
      }
    }

    fetchSummaryDetail()
  }, [summaryId, setSummaryTitle, setVoiceUrls, voiceUrls, reset])

  const [isModalOpen, setIsModalOpen] = useState(false)
  const [modalFlag, setModalFlag] = useState(false)
  const [isTableOpen, setIsTableOpen] = useState(false)
  const [selectedSentenceId, setSelectedSentenceId] = useState(null)
  const [loadingSentenceId, setLoadingSentenceId] = useState(null)

  const handleDelete = async (sentenceId) => {
    try {
      await sentencesApi.deleteSentence(sentenceId)
      setSentences(sentences.filter((sentence) => sentence.id !== sentenceId))
    } catch (error) {
      console.error('Error deleting sentence:', error)
    }
  }

  const handleFolding = async (sentenceId) => {
    try {
      const currentSentence = sentences.find(
        (sentence) => sentence.id === sentenceId
      )
      await sentencesApi.postSentenceFolding(
        sentenceId,
        currentSentence.openStatus
      )
      console.log('success')
      setSentences(
        sentences.map((sentence) =>
          sentence.id === sentenceId
            ? {
                ...sentence,
                openStatus: !sentence.openStatus,
              }
            : sentence
        )
      )
    } catch (error) {
      console.error('Error folding sentence:', error)
    }
  }

  const handleNewSummary = async (sentenceId) => {
    try {
      setLoadingSentenceId(sentenceId)
      await sentencesApi.patchSentenceNew(sentenceId)
      const data = await summariesApi.getSummariesDetail(summaryId)
      setSentences(data.sentenceResponseList)
    } catch (error) {
      console.error('Error requesting new summary:', error)
    } finally {
      setLoadingSentenceId(null)
    }
  }

  const handleDetailSummary = async (sentenceId) => {
    try {
      setLoadingSentenceId(sentenceId)
      await sentencesApi.patchSentenceDetail(sentenceId)
      const data = await summariesApi.getSummariesDetail(summaryId)
      setSentences(data.sentenceResponseList)
    } catch (error) {
      console.error('Error requesting detailed summary:', error)
    } finally {
      setLoadingSentenceId(null)
    }
  }

  const handleShortPress = (sentenceId, sentenceURL, status) => {
    if (!status) {
      setModalFlag(false)
      setSelectedSentenceId(sentenceId)
      setTimeout(() => setIsModalOpen(true), 50)
      return
    }
    if (sentenceURL) {
      setCurrentIndex(sentenceId - 1)
    }
  }

  const handleLongPress = (sentenceId, status) => {
    setModalFlag(!status ? false : true)
    setIsModalOpen(true)
    setSelectedSentenceId(sentenceId)
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

  const downloadAudioFile = async (url) => {
    try {
      const response = await fetch(url)

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }

      const blob = await response.blob()

      // Blob 데이터를 Audio 태그나 로컬스토리지에 저장하기 위해 변환
      const audioURL = URL.createObjectURL(blob) // 오디오 재생용 URL 생성
      return audioURL
    } catch (error) {
      console.error('Failed to download audio:', error)
      return null
    }
  }

  const [summaryMode, setSummaryMode] = useState('normal')

  const modeMenuItems = [
    { title: '상세', mode: 'detail' },
    { title: '키워드', mode: 'keyword' },
    { title: '일반', mode: 'normal' },
  ]

  const handleSummaryMode = (mode) => {
    setSummaryMode(mode)
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
      <ModeSelectWrapper>
        <BookmarkMenu
          summaryMode={summaryMode}
          onButtonClick={handleSummaryMode}
          menuItems={modeMenuItems}
        />
      </ModeSelectWrapper>
      <Main>
        <ContentArea id="content-area">
          {sentences.map((sentence) => (
            <Element name={`sentence-${sentence.id}`} key={sentence.id}>
              <Sentence
                text={sentence.content}
                status={sentence.openStatus}
                onShortPress={() =>
                  handleShortPress(
                    sentence.id,
                    sentence.voiceUrl,
                    sentence.openStatus
                  )
                }
                onLongPress={() =>
                  handleLongPress(sentence.id, sentence.openStatus)
                }
              />
              {loadingSentenceId === sentence.id && <Loading />}
            </Element>
          ))}
        </ContentArea>
      </Main>
      <Modal
        isOpen={isModalOpen}
        onClose={closeModal}
        flag={modalFlag}
        onDelete={() => handleDelete(selectedSentenceId)}
        onFolding={() => handleFolding(selectedSentenceId)}
        onNewSummary={() => handleNewSummary(selectedSentenceId)}
        onDetailSummary={() => handleDetailSummary(selectedSentenceId)}
      />
    </Container>
  )
}

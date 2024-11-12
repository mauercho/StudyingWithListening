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

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  width: 100%;
  height: 85vh;
`

const HeaderWrapper = styled.div`
  width: 100%;
  max-width: 768px;
  background: ${({ theme }) => theme.color.white};
  z-index: 80;
  border-radius: 0 0 16px 16px;
`

const ContentArea = styled.ul`
  flex: 1;
  width: 100%;
  max-width: 768px;
  padding: 10px;
  margin-top: 10px;
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

  const [sentences, setSentences] = useState([
    {
      id: 1,
      content:
        '컴퓨터는 이진수로 데이터를 표현하며, 0과 1로 이루어진 비트를 사용합니다.',
      openStatus: 'normal',
    },
    {
      id: 2,
      content: '알고리즘은 문제 해결을 위한 명확한 절차 또는 단계입니다.',
      openStatus: 'hidden',
    },
  ])

  // useEffect(() => {
  //   const fetchSummaryDetail = async () => {
  //     try {
  //       const data = await summariesApi.getSummariesDetail(summaryId)
  //       setSentences(data.sentenceResponseList)

  //       // 새로운 디테일 페이지일 때만 초기화
  //       if (voiceUrls.length === 0 || data.summaryId !== summaryId) {
  //         reset()
  //         setSummaryTitle(data.summaryTitle)
  //         const urls = data.sentenceResponseList.map(
  //           (sentence) => sentence.voiceUrl
  //         )
  //         setVoiceUrls(urls)
  //       }
  //     } catch (error) {
  //       console.error('Error:', error)
  //     }
  //   }

  //   fetchSummaryDetail()
  // }, [summaryId, setSummaryTitle, setVoiceUrls, reset])

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
      <ContentArea id="content-area">
        {sentences.map(
          (
            sentence,
            index // index 파라미터 추가
          ) => (
            <Element name={`sentence-${sentence.id}`} key={sentence.id}>
              <Sentence
                text={sentence.content}
                status={sentence.openStatus}
                index={index}
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
          )
        )}
      </ContentArea>
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

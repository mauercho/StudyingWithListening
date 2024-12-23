import React, { useState, useEffect } from 'react'

import { useParams } from 'react-router-dom'
import styled from '@emotion/styled'
import { scroller, Element } from 'react-scroll'
import { BsQuestionSquareFill } from 'react-icons/bs'
import { FaLightbulb } from 'react-icons/fa'
import { FaFireAlt } from 'react-icons/fa'
import { FaFileAlt } from 'react-icons/fa'

import TableOfContents from '../components/TableOfContents'
import Sentence from '../components/Sentence'
import Modal from '../components/Modal'
import sentencesApi from '../api/sentencesApi'
import summariesApi from '../api/summariesApi'
import Loading from '../components/Loading'
import usePlayerStore from '../stores/usePlayerStore'
import HelpModal from '../components/HelpModal'
import ModeModal from '../components/ModeModal'
import ContentsModal from '../components/ContentsModal'
import theme from '../assets/styles/theme'

const Container = styled.div`
  width: 100%;
  overflow-y: auto;
  box-sizing: border-box;
`
const QuestionIcon = styled(BsQuestionSquareFill)`
  color: ${({ theme }) => theme.color.primary_dark};
  ${({ mode }) =>
    mode === 'simple' &&
    `
      color: ${theme.color.secondary};
    `}
  font-size: 30px;
`

const FileIcon = styled(FaFileAlt)`
  color: ${theme.color.primary};
`

const TableOfContentsWrapper = styled.div`
  width: 100%;
  max-width: 768px;
  top: 60px;
  position: fixed;
  background: ${({ theme }) => theme.color.white};
  z-index: 80;
  border-radius: 0 0 16px 16px;
  padding: 0;
  box-sizing: border-box;
`

const HeaderWrapper = styled.div`
  top: 100px;
  position: fixed;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid ${theme.color.grey_dark};
  box-sizing: border-box;
`

const Main = styled.div`
  position: fixed;
  width: 100%;
  top: 152px;
  bottom: 67px;
  overflow-y: scroll;
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
`

const QnA = styled(Element)`
  display: flex;
  flex-direction: column;
  padding: 10px;
  gap: 10px;
`

const SubTitle = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
`

const SubTitleText = styled.p`
  font-size: ${theme.font.size['2xl']};
  font-weight: ${theme.font.weight.bold};
`

const IconWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 4px;
`

const ModeSelectButton = styled.button`
  width: 84px;
  background: ${({ theme }) => theme.color.white};
  padding: 4px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: ${theme.color.primary_dark};
  border: 1px solid ${theme.color.primary_dark};
  font-size: ${theme.font.size.lg};
  font-weight: ${theme.font.weight.medium};
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  box-sizing: border-box;
  ${({ mode }) =>
    mode === 'simple' &&
    `
        color: ${theme.color.secondary};
        border: 1px solid ${theme.color.secondary};
    `}
`

export default function Detail() {
  const { summaryId } = useParams()
  const [indexes, setIndexes] = useState([])

  const {
    setSummaryTitle,
    setVoiceUrls,
    setCurrentIndex,
    setIsPlaying,
    voiceUrls,
    reset,
  } = usePlayerStore()

  const [sentences, setSentences] = useState([])
  const [summaryMode, setSummaryMode] = useState('detail')
  const [isModalOpen, setIsModalOpen] = useState(false)
  const [modalFlag, setModalFlag] = useState(false)
  const [isTableOpen, setIsTableOpen] = useState(false)
  const [selectedSentenceId, setSelectedSentenceId] = useState(null)
  const [loadingSentenceId, setLoadingSentenceId] = useState(null)
  const [isHelpModalOpen, setIsHelpModalOpen] = useState(false)
  const [isModeModalOpen, setIsModeModalOpen] = useState(false)
  const [isUploadedModalOpen, setIsUploadedModalOpen] = useState(false)
  const [imageUrl, setImageUrl] = useState('')

  useEffect(() => {
    const fetchSummaryDetail = async () => {
      try {
        const data = await summariesApi.getSummariesDetail(summaryId)
        console.log(data)
        // summaryMode에 따라 맞는 content와 voiceUrl을 선택
        const updatedSentences = data.sentenceResponseList.map((sentence) => {
          let content, voiceUrl
          switch (summaryMode) {
            case 'detail':
              content = sentence.normalAnswer
              voiceUrl = sentence.normalAnswerVoiceUrl
              break
            case 'simple':
              content = sentence.simpleAnswer
              voiceUrl = sentence.simpleAnswerVoiceUrl
              break
            default:
              content = sentence.normalAnswer
              voiceUrl = sentence.normalAnswerVoiceUrl
              break
          }
          return {
            ...sentence,
            content,
            voiceUrl,
            questionVoiceUrl: sentence.questionVoiceUrl,
          }
        })

        const updatedIndexes = data.sentenceResponseList.map((sentence) => ({
          indexTitle: sentence.sentencePoint,
          sentenceOrder: sentence.order,
        }))

        const newUrls = updatedSentences.flatMap((sentence) => [
          sentence.questionVoiceUrl,
          sentence.voiceUrl,
        ])

        if (voiceUrls.length === 0) {
          setSummaryTitle(data.summaryTitle)
          setVoiceUrls(newUrls)
        } else if (!newUrls.some((url) => voiceUrls.includes(url))) {
          reset()
          setSummaryTitle(data.summaryTitle)
          setVoiceUrls(newUrls)
        } else {
          setSummaryTitle(data.summaryTitle)
        }
        setSentences(updatedSentences)
        setIndexes(updatedIndexes)
        setCurrentIndex(updatedSentences[0].order - 1)
        setImageUrl(data.uploadContentUrl)
      } catch (error) {
        console.error('Error:', error)
      }
    }

    fetchSummaryDetail()
  }, [
    summaryId,
    summaryMode,
    setCurrentIndex,
    setSummaryTitle,
    setVoiceUrls,
    voiceUrls,
    reset,
  ])

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

  const handleShortPress = (sentenceOrder, sentenceURL, status) => {
    if (!status) {
      setModalFlag(false)
      setSelectedSentenceId(sentenceOrder)
      setTimeout(() => setIsModalOpen(true), 50)
      return
    }
    if (sentenceURL) {
      const index =
        status === 'question'
          ? (sentenceOrder - 1) * 2
          : (sentenceOrder - 1) * 2 + 1
      setCurrentIndex(index)
      setIsPlaying(true)
    }
  }

  const handleLongPress = (sentenceId, status) => {
    // setModalFlag(!status ? false : true)
    // setIsModalOpen(true)
    setSelectedSentenceId(sentenceId)
  }

  const handleTableTouch = (sentenceOrder) => {
    if (sentenceOrder && sentenceOrder > 0) {
      setCurrentIndex((sentenceOrder - 1) * 2)
      setIsPlaying(true)
    }
    scroller.scrollTo(`sentence-${sentenceOrder}`, {
      containerId: 'content-area',
      duration: 500,
      delay: 0,
      smooth: 'easeInOutQuart',
      offset: -112,
    })
  }

  const toggleTable = () => {
    setIsTableOpen((prev) => !prev)
  }

  const closeModal = () => {
    setIsModalOpen(false)
  }

  const handleSummaryMode = (mode) => {
    setSummaryMode(mode)
    setIsModeModalOpen(false)
  }

  return (
    <Container>
      <HelpModal
        isOpen={isHelpModalOpen}
        onClose={() => setIsHelpModalOpen(false)}
        onButtonClick={() => {
          setIsHelpModalOpen(false)
          setIsModeModalOpen(true)
        }}
      />
      <ModeModal
        isOpen={isModeModalOpen}
        onClose={() => setIsModeModalOpen(false)}
        onSelectMode={handleSummaryMode}
      />
      <ContentsModal
        isOpen={isUploadedModalOpen}
        onClose={() => setIsUploadedModalOpen(false)}
        url={imageUrl}
      />
      <TableOfContentsWrapper>
        <TableOfContents
          indexes={indexes}
          onButtonClick={handleTableTouch}
          isOpen={isTableOpen}
          toggleOpen={toggleTable}
        />
      </TableOfContentsWrapper>
      <HeaderWrapper>
        <IconWrapper>
          <QuestionIcon
            onClick={() => setIsHelpModalOpen(true)}
            mode={summaryMode}
            size={30}
          />
          <FileIcon onClick={() => setIsUploadedModalOpen(true)} size={30} />
        </IconWrapper>
        <SubTitle>
          {summaryMode === 'simple' ? (
            <FaFireAlt size={25} color={'#FF0000'} />
          ) : (
            <FaLightbulb size={25} color={'#FFB902'} />
          )}
          <SubTitleText>
            {summaryMode === 'simple' ? '시험 5분 전' : '연상 암기'}
          </SubTitleText>
        </SubTitle>
        <ModeSelectButton
          mode={summaryMode}
          onClick={() => setIsModeModalOpen(true)}
        >
          모드 선택
        </ModeSelectButton>
      </HeaderWrapper>
      <Main>
        <ContentArea id="content-area">
          {sentences.map((sentence) => (
            <QnA name={`sentence-${sentence.order}`} key={sentence.id}>
              <Sentence
                mode={summaryMode}
                text={sentence.question}
                status={'question'}
                index={(sentence.order - 1) * 2}
                onShortPress={() =>
                  handleShortPress(
                    sentence.order,
                    sentence.voiceUrl,
                    'question'
                  )
                }
                onLongPress={() =>
                  handleLongPress(sentence.id, sentence.openStatus)
                }
              />
              <Sentence
                mode={summaryMode}
                text={sentence.content}
                status={'answer'}
                index={(sentence.order - 1) * 2 + 1}
                onShortPress={() =>
                  handleShortPress(sentence.order, sentence.voiceUrl, 'answer')
                }
                onLongPress={() =>
                  handleLongPress(sentence.id, sentence.openStatus)
                }
              />
              {loadingSentenceId === sentence.id && <Loading />}
            </QnA>
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

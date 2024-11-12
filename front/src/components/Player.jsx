import React, { useRef, useEffect } from 'react'

import styled from '@emotion/styled'
import {
  MdOutlinePause,
  MdOutlinePlayArrow,
  MdOutlineSkipNext,
  MdOutlineSkipPrevious,
} from 'react-icons/md'

import usePlayerStore from '../stores/usePlayerStore'

const Container = styled.div`
  background-color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100vw;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
`

const PlayerWrapper = styled.div`
  background-color: white;
  width: 100%;
  padding: 20px;
  border-top: 1px solid ${({ theme }) => theme.color.primary_dark || '#333'};
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-sizing: border-box;
`

const ControlsWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
`

const IconButton = styled.button`
  background: none;
  border: none;
  color: ${({ theme }) => theme.color.primary || '#333'};
  cursor: pointer;
  font-size: 24px;
  display: flex;
  align-items: center;
  justify-content: center;
`

const TitleSection = styled.div`
  display: flex;
  flex-direction: column;
  width: 200px;
  color: ${({ theme }) => theme.color.primary || '#333'};
`

const Title = styled.div`
  font-weight: ${({ theme }) => theme.font?.weight?.regular || '500'};
  font-size: ${({ theme }) => theme.font?.size?.lg || '16px'};
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`

export default function Player() {
  const audioRef = useRef(null)
  const {
    summaryTitle,
    voiceUrls,
    currentIndex,
    currentVoiceUrl,
    setCurrentIndex,
    isPlaying,
    setIsPlaying,
  } = usePlayerStore()
  // const [isPlaying, setIsPlaying] = useState(false)

  const handlePlayPause = () => {
    if (!currentVoiceUrl && voiceUrls.length > 0) {
      // 재생 중인 음성이 없고 음성 URL이 있으면 첫 번째 음성부터 재생
      setCurrentIndex(0)
      setIsPlaying(true)
      return
    }

    if (audioRef.current.paused) {
      audioRef.current.play()
      setIsPlaying(true)
    } else {
      audioRef.current.pause()
      setIsPlaying(false)
    }
  }

  const handleSkipForward = () => {
    if (currentIndex < voiceUrls.length - 1) {
      setCurrentIndex(currentIndex + 1)
    }
  }

  const handleSkipBackward = () => {
    if (currentIndex > 0) {
      setCurrentIndex(currentIndex - 1)
    }
  }
  useEffect(() => {
    if (audioRef.current) {
      audioRef.current.onended = () => {
        if (currentIndex < voiceUrls.length - 1) {
          setCurrentIndex(currentIndex + 1)
        }
      }
    }
  }, [currentIndex, voiceUrls.length, setCurrentIndex])
  useEffect(() => {
    if (audioRef.current && currentVoiceUrl) {
      audioRef.current.src = currentVoiceUrl
      audioRef.current.load()
      audioRef.current.play() // 항상 재생
      setIsPlaying(true)
    }
  }, [currentVoiceUrl, setIsPlaying])
  useEffect(() => {
    if (audioRef.current) {
      if (!isPlaying) {
        audioRef.current.pause()
      }
    }
  }, [isPlaying])

  return (
    <Container>
      <audio ref={audioRef} preload="auto" style={{ display: 'none' }} />
      <PlayerWrapper>
        <TitleSection>
          <Title>{summaryTitle}</Title>
        </TitleSection>
        <ControlsWrapper>
          <IconButton onClick={handleSkipBackward}>
            <MdOutlineSkipPrevious />
          </IconButton>
          <IconButton onClick={handlePlayPause}>
            {isPlaying ? <MdOutlinePause /> : <MdOutlinePlayArrow />}
          </IconButton>
          <IconButton onClick={handleSkipForward}>
            <MdOutlineSkipNext />
          </IconButton>
        </ControlsWrapper>
      </PlayerWrapper>
    </Container>
  )
}

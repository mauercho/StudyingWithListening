import React, { useRef, useEffect, useState } from 'react'
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
  const { summaryTitle, voiceUrl } = usePlayerStore()
  const [isPlaying, setIsPlaying] = useState(false)

  const handlePlayPause = () => {
    if (audioRef.current.paused) {
      audioRef.current.play()
      setIsPlaying(true)
    } else {
      audioRef.current.pause()
      setIsPlaying(false)
    }
  }

  const handleSkipForward = () => {
    // 이후 구현될 기능에 맞춰 인덱스 변경 로직 추가 가능
    console.log('Skip forward')
  }

  const handleSkipBackward = () => {
    // 이전 구현될 기능에 맞춰 인덱스 변경 로직 추가 가능
    console.log('Skip backward')
  }

  useEffect(() => {
    if (audioRef.current && voiceUrl) {
      audioRef.current.src = voiceUrl
      audioRef.current.load()
      audioRef.current.play()
      setIsPlaying(true)
    }
  }, [voiceUrl])

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

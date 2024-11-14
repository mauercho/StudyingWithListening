import React, { useRef, useEffect, useState, useCallback, useMemo } from 'react'

import styled from '@emotion/styled'
import {
  MdOutlinePause,
  MdOutlinePlayArrow,
  MdOutlineSkipNext,
  MdOutlineSkipPrevious,
  MdOutlineSettings,
} from 'react-icons/md'

import usePlayerStore from '../stores/usePlayerStore'
import PopUpMenu from './PopUpMenu'

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
  position: relative;
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
  const settingsButtonRef = useRef(null)
  const {
    summaryTitle,
    voiceUrls,
    currentIndex,
    currentVoiceUrl,
    setCurrentIndex,
    isPlaying,
    setIsPlaying,
  } = usePlayerStore()
  const [playbackRate, setPlaybackRate] = useState(1.0)
  const [isMenuOpen, setIsMenuOpen] = useState(false)

  const handlePlaybackRateChange = useCallback((rate) => {
    if (audioRef.current) {
      audioRef.current.playbackRate = rate
      setPlaybackRate(rate)
      setIsMenuOpen(false)
    }
  }, [])
  const handleSkipForward = useCallback(() => {
    if (currentIndex < voiceUrls.length - 1) {
      setCurrentIndex(currentIndex + 1)
    }
  }, [currentIndex, voiceUrls.length, setCurrentIndex])

  const handleSkipBackward = useCallback(() => {
    if (currentIndex > 0) {
      setCurrentIndex(currentIndex - 1)
    }
  }, [currentIndex, setCurrentIndex])
  const speedItems = useMemo(
    () => [
      {
        text: '0.5x',
        onClick: () => handlePlaybackRateChange(0.5),
        isSelected: playbackRate === 0.5,
      },
      {
        text: '1.0x',
        onClick: () => handlePlaybackRateChange(1.0),
        isSelected: playbackRate === 1.0,
      },
      {
        text: '1.5x',
        onClick: () => handlePlaybackRateChange(1.5),
        isSelected: playbackRate === 1.5,
      },
      {
        text: '2.0x',
        onClick: () => handlePlaybackRateChange(2.0),
        isSelected: playbackRate === 2.0,
      },
    ],
    [playbackRate, handlePlaybackRateChange]
  )

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        settingsButtonRef.current &&
        !settingsButtonRef.current.contains(event.target)
      ) {
        setIsMenuOpen(false)
      }
    }

    if (isMenuOpen) {
      document.addEventListener('mousedown', handleClickOutside)
    }

    return () => {
      document.removeEventListener('mousedown', handleClickOutside)
    }
  }, [isMenuOpen])
  useEffect(() => {
    if ('mediaSession' in navigator && 'MediaMetadata' in window) {
      navigator.mediaSession.metadata = new window.MediaMetadata({
        title: summaryTitle,
        artist: `숏공`,
        album: '',
      })
    }
  }, [currentIndex, summaryTitle, voiceUrls.length])
  useEffect(() => {
    if ('mediaSession' in navigator) {
      navigator.mediaSession.setActionHandler('seekto', null)
      navigator.mediaSession.setActionHandler('seekforward', null)
      navigator.mediaSession.setActionHandler('seekbackward', null)
      navigator.mediaSession.setActionHandler('previoustrack', null)
      navigator.mediaSession.setActionHandler('nexttrack', null)
      navigator.mediaSession.setActionHandler('play', null)
      navigator.mediaSession.setActionHandler('pause', null)
      navigator.mediaSession.setActionHandler(
        'previoustrack',
        handleSkipBackward
      )
      navigator.mediaSession.setActionHandler('nexttrack', handleSkipForward)
      navigator.mediaSession.setActionHandler('play', () => {
        audioRef.current.play()
        setIsPlaying(true)
      })
      navigator.mediaSession.setActionHandler('pause', () => {
        audioRef.current.pause()
        setIsPlaying(false)
      })
    }
    navigator.mediaSession.setPositionState({
      duration: audioRef.current?.duration || 0,
      playbackRate: playbackRate,
      position: audioRef.current?.currentTime || 0,
    })
  }, [
    currentIndex,
    voiceUrls.length,
    summaryTitle,
    handleSkipBackward,
    handleSkipForward,
    playbackRate,
    setIsPlaying,
  ])
  useEffect(() => {
    if ('mediaSession' in navigator) {
      navigator.mediaSession.playbackState = isPlaying ? 'playing' : 'paused'
    }
  }, [isPlaying])
  const handlePlayPause = () => {
    if (!currentVoiceUrl && voiceUrls.length > 0) {
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

  // const handleSkipForward = () => {
  //   if (currentIndex < voiceUrls.length - 1) {
  //     setCurrentIndex(currentIndex + 1)
  //   }
  // }

  // const handleSkipBackward = () => {
  //   if (currentIndex > 0) {
  //     setCurrentIndex(currentIndex - 1)
  //   }
  // }

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
      if (isPlaying) {
        audioRef.current.play()
        audioRef.current.playbackRate = playbackRate
      }
    }
  }, [currentVoiceUrl, isPlaying, playbackRate])
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
          <IconButton
            ref={settingsButtonRef}
            onClick={(e) => {
              e.stopPropagation()
              setIsMenuOpen((prev) => !prev)
            }}
          >
            <MdOutlineSettings />
          </IconButton>
          <PopUpMenu
            triggerRef={settingsButtonRef}
            isOpen={isMenuOpen}
            onClose={() => setIsMenuOpen(false)}
            items={speedItems}
            location="l"
          />
        </ControlsWrapper>
      </PlayerWrapper>
    </Container>
  )
}

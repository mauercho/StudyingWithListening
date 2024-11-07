import React, { useState, useRef, useEffect, useCallback } from 'react'

import styled from '@emotion/styled'
import {
  MdOutlinePause,
  MdOutlinePlayArrow,
  MdOutlineSkipNext,
  MdOutlineSkipPrevious,
  MdOutlineSettings,
} from 'react-icons/md'

import theme from '../assets/styles/theme'
// 이거는 실험용 음악파일 입니다.
import fade from '../music/As You Fade Away - NEFFEX.mp3'
import enough from '../music/Enough - NEFFEX.mp3'
import immortal from '../music/Immortal - NEFFEX.mp3'
import playDead from '../music/Play Dead - NEFFEX.mp3'
import winning from '../music/Winning - NEFFEX.mp3'

const Container = styled.div`
  background-color: white;
  height: 65px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`

const PlayerWrapper = styled.div`
  background-color: white;
  width: 100%;
  padding: 20px;
  border-top: 1px solid ${theme.color.primary_dark};
  display: flex;
  justify-content: space-between;
  align-items: center;
`

const ControlsWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0;
  margin-right: 20px;
  height: 100%;
`

const TitleSection = styled.div`
  display: flex;
  color: ${theme.color.primary};
  margin-left: 20px;
  flex-direction: column;
  width: 200px;
`
const Title = styled.div`
  font-weight: ${theme.font.weight.regular};
  font-size: ${theme.font.size.lg};
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`
const SubInfoWrapper = styled.div`
  display: flex;
  font-weight: ${theme.font.weight.regular};
  font-size: ${theme.font.size.xxs};
  gap: 30px;
`

const IconButton = styled.button`
  background: none;
  border: none;
  color: ${theme.color.primary};
  padding: 0 3px;
  display: flex;
  align-items: center;
  justify-content: center;

  &:hover {
    color: ${theme.color.primary};
  }

  &:active {
    color: ${theme.color.black};
  }

  svg {
    font-size: 24px;
  }
`

const playlist = [
  {
    file: fade,
    title: 'As You Fade Away',
    artist: 'sdaofasdiofnsdaofnasdoinfsd',
  },
  { file: enough, title: 'Enough', artist: '2' },
  { file: immortal, title: 'Immortal', artist: '3' },
  { file: playDead, title: 'Play Dead', artist: 'sadfsdfsdfasdf' },
  { file: winning, title: 'Winning', artist: '5' },
]

export default function Player() {
  const audioPlayer = useRef()
  const [index, setIndex] = useState(0)
  const [currentSong] = useState(playlist[index].file)
  const [isPlaying, setIsPlaying] = useState(false)
  const truncateText = (text, maxLength) => {
    if (text.length > maxLength) {
      return text.slice(0, maxLength) + '...'
    }
    return text
  }
  const togglePlay = useCallback(() => {
    if (!isPlaying) {
      audioPlayer.current.play()
    } else {
      audioPlayer.current.pause()
    }
    setIsPlaying((prev) => !prev)
  }, [isPlaying])

  const toggleSkipForward = useCallback(() => {
    if (index >= playlist.length - 1) {
      setIndex(0)
      audioPlayer.current.src = playlist[0].file
    } else {
      setIndex((prev) => prev + 1)
      audioPlayer.current.src = playlist[index + 1].file
    }
    audioPlayer.current.play()
    setIsPlaying(true)
  }, [index])

  const toggleSkipBackward = useCallback(() => {
    if (index > 0) {
      setIndex((prev) => prev - 1)
      audioPlayer.current.src = playlist[index - 1].file
      audioPlayer.current.play()
      setIsPlaying(true)
    } else if (index === 0) {
      setIndex(playlist.length - 1)
      audioPlayer.current.src = playlist[playlist.length - 1].file
      audioPlayer.current.play()
      setIsPlaying(true)
    }
  }, [index])

  useEffect(() => {
    if ('mediaSession' in navigator && 'MediaMetadata' in window) {
      navigator.mediaSession.metadata = new window.MediaMetadata({
        title: truncateText(playlist[index].title, 50),
        artist: truncateText(playlist[index].artist, 40),
        album: '',
      })

      navigator.mediaSession.setPositionState(null)
      navigator.mediaSession.setActionHandler('seekto', null)
      navigator.mediaSession.setActionHandler('seekforward', null)
      navigator.mediaSession.setActionHandler('seekbackward', null)
      navigator.mediaSession.setActionHandler(
        'previoustrack',
        toggleSkipBackward
      )
      navigator.mediaSession.setActionHandler('nexttrack', toggleSkipForward)
      navigator.mediaSession.setActionHandler('play', () => {
        audioPlayer.current.play()
        setIsPlaying(true)
      })
      navigator.mediaSession.setActionHandler('pause', () => {
        audioPlayer.current.pause()
        setIsPlaying(false)
      })
    }
  }, [toggleSkipBackward, toggleSkipForward, index])

  useEffect(() => {
    if ('mediaSession' in navigator) {
      navigator.mediaSession.playbackState = isPlaying ? 'playing' : 'paused'
    }
  }, [isPlaying])

  return (
    <Container>
      <audio
        src={currentSong}
        ref={audioPlayer}
        preload="auto"
        playsInline
        controlsList="noplaybackrate noseek nodownload"
        controls={false}
        onEnded={toggleSkipForward}
      />

      <PlayerWrapper>
        <TitleSection>
          <Title>{truncateText(playlist[index].title, 20)}</Title>
          <SubInfoWrapper>
            <span>{truncateText(playlist[index].artist, 15)}</span>
            <span>
              {index + 1}/{playlist.length}
            </span>
          </SubInfoWrapper>
        </TitleSection>
        <ControlsWrapper>
          <IconButton onClick={toggleSkipBackward}>
            <MdOutlineSkipPrevious />
          </IconButton>

          <IconButton className="play-button" onClick={togglePlay}>
            {!isPlaying ? <MdOutlinePlayArrow /> : <MdOutlinePause />}
          </IconButton>

          <IconButton onClick={toggleSkipForward}>
            <MdOutlineSkipNext />
          </IconButton>

          <IconButton>
            <MdOutlineSettings />
          </IconButton>
        </ControlsWrapper>
      </PlayerWrapper>
    </Container>
  )
}

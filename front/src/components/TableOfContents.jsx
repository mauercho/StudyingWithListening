import React, { useState, useEffect, useRef } from 'react'

import styled from '@emotion/styled'

import { FaGripLines } from 'react-icons/fa'
import usePlayerStore from '../stores/usePlayerStore'


const Container = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
`

const Navigation = styled.nav`
  height: ${({ isOpen }) => (isOpen ? '272px' : '0')};
  background-color: ${({ theme }) => theme.color.white};
  overflow-y: auto;
  transition: height 0.3s ease-in-out;
`

const TableButton = styled.button`
  width: 100%;
  padding: 10px;
  display: flex;
  flex-direction: column;
  align-items: start;
  cursor: pointer;
  background-color: ${({ theme }) => theme.color.white};
  border: 0;
  font: inherit;

  &:active {
    background-color: ${({ theme }) => theme.color.grey_dark};
  }

  p {
    ${({ theme }) => `
      color: ${theme.color.black};
      font-weight: ${theme.font.weight.light};
      font-size: ${theme.font.size.lg};
    `}
  }

  ${({ isPlaying, theme }) =>
    isPlaying &&
    `
      background-color: ${theme.color.primary_light};
      p {
        color: ${theme.color.primary_dark};
        font-weight: ${theme.font.weight.medium};
      }
    `}
`

const ToggleButton = styled.button`
  background: none;
  border: none;
  cursor: pointer;
  background-color: ${({ theme }) => theme.color.white};
  border-radius: 0 0 16px 16px;
  &:active {
    background-color: ${({ theme }) => theme.color.grey_dark};
  }

  ${({ theme, isOpen }) =>
    !isOpen
      ? `
        border-left: 1px solid ${theme.color.primary};
        border-right: 1px solid ${theme.color.primary};
        border-bottom: 1px solid ${theme.color.primary};
      `
      : `
        box-shadow: 0px 8px 12px rgba(0, 0, 0, 0.15);
      `}
`

export default function TableOfContents({ indexes, onButtonClick }) {
  const currentSentenceId = null
  const [isOpen, setIsOpen] = useState(false)
  const startY = useRef(null)
  const toggleButtonRef = useRef(null)
  const { currentIndex } = usePlayerStore()

  const handleTouchStart = (e) => {
    startY.current = e.touches[0].clientY
  }

  const handleTouchMove = (e) => {
    if (startY.current === null) return
    const endY = e.touches[0].clientY
    const deltaY = startY.current - endY

    if (deltaY > 10 && isOpen) {
      setIsOpen(false) // 위로 스크롤 시 닫기
      startY.current = null
    } else if (
      deltaY < -10 &&
      !isOpen &&
      toggleButtonRef.current.contains(e.target)
    ) {
      setIsOpen(true) // 아래로 스크롤 시 열기 (버튼 위에서만)
      startY.current = null
    }
  }

  useEffect(() => {
    window.addEventListener('touchstart', handleTouchStart)
    window.addEventListener('touchmove', handleTouchMove)

    return () => {
      window.removeEventListener('touchstart', handleTouchStart)
      window.removeEventListener('touchmove', handleTouchMove)
    }
  }, [isOpen])

  return (
    <Container>
      <Navigation isOpen={isOpen}>
        <ul>
          {indexes.map((item) => (
            <li key={item.sentenceOrder}>
              <TableButton
                onClick={() => onButtonClick(item.sentenceOrder)}
                isPlaying={item.sentenceOrder * 2 - 1 === currentIndex || item.sentenceOrder * 2 - 1 === currentIndex + 1}
              >
                <p>{item.indexTitle}</p>
              </TableButton>
            </li>
          ))}
        </ul>
      </Navigation>
      <ToggleButton
        ref={toggleButtonRef}
        onClick={() => setIsOpen(!isOpen)}
        isOpen={isOpen}
      >
        <FaGripLines size={24} />
      </ToggleButton>
    </Container>
  )
}

import React, { useState, useEffect, useRef } from 'react'

import styled from '@emotion/styled'

import { FaGripLines } from 'react-icons/fa'
import { FaHeadphones } from 'react-icons/fa'
import usePlayerStore from '../stores/usePlayerStore'
import theme from '../assets/styles/theme'

const Container = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
`

const Navigation = styled.nav`
  height: ${({ isOpen }) => (isOpen ? '170px' : '0')};
  background-color: ${({ theme }) => theme.color.white};
  overflow-y: scroll;
  transition: height 0.3s ease-in-out;
`

const TableButton = styled.button`
  width: 100%;
  display: flex;
  flex-direction: row;
  gap: 10px;
  padding: 10px;
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
      font-weight: ${theme.font.weight.regular};
      font-size: ${theme.font.size.base};
    `}
  }
`

const IconFrame = styled.div`
  width: 22px;
  height: 22px;
`

const ToggleButton = styled.button`
  background: none;
  border: none;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  padding: 4px 10px;
  background-color: ${({ theme }) => theme.color.white};
  border-radius: 0 0 16px 16px;
  &:active {
    background-color: ${({ theme }) => theme.color.grey_dark};
  }

  ${({ theme, isOpen }) =>
    !isOpen
      ? `
        border: 1px solid ${theme.color.primary};
        border-top: 0;
      `
      : `
        box-shadow: 0px 8px 12px rgba(0, 0, 0, 0.15);
      `}

  p {
    font-size: ${theme.font.size.lg};
  }
`

export default function TableOfContents({ indexes, onButtonClick }) {
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
    // eslint-disable-next-line
  }, [isOpen])
  return (
    <Container>
      <Navigation isOpen={isOpen}>
        <ul>
          {indexes.map((item) => (
            <li key={item.sentenceOrder}>
              <TableButton
                onClick={() => onButtonClick(item.sentenceOrder)}
                isPlaying={
                  item.sentenceOrder * 2 - 1 === currentIndex ||
                  item.sentenceOrder * 2 - 1 === currentIndex + 1
                }
              >
                <IconFrame>
                  {item.sentenceOrder * 2 - 2 === currentIndex && (
                    <FaHeadphones size={16} />
                  )}
                  {item.sentenceOrder * 2 - 1 === currentIndex && (
                    <FaHeadphones size={16} />
                  )}
                </IconFrame>
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
        {!isOpen && <p>목차</p>}
        <FaGripLines size={24} />
      </ToggleButton>
    </Container>
  )
}

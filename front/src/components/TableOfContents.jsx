import React, { useState } from 'react'

import styled from '@emotion/styled'
import { FaGripLines } from 'react-icons/fa'

const Container = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  gap: 14px;
  margin-bottom: -20px;
`

const Navigation = styled.nav`
  height: 272px;
  display: ${({ isOpen }) => (isOpen ? 'block' : 'none')};
  overflow-y: auto;
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
    text-decoration: none;
    ${({ theme }) =>
      `
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
  height: 24px;
  background: none;
  border: none;
  padding: 0;
  margin: 0;
  cursor: pointer;
  margin-top: ${({ isOpen }) => (isOpen ? '-20px' : '0')};
  border-radius: 0 0 16px 16px;
  background-color: ${({ theme }) => theme.color.white};
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

// TODO: 1104 기준 API대로 작성. 이후 상태관리 기반 현재 문장 활성화, API 변경시 다른 요소 추가 필요
export default function TableOfContents({ indexes, onButtonClick }) {
  const currentSentenceId = null // 현재 재생중인 문장의 id를 받을 수단 필요(상태관리..?)
  const [isOpen, setIsOpen] = useState(false)

  return (
    <Container>
      <Navigation isOpen={isOpen}>
        <ul>
          {indexes.map((item) => (
            <li key={item.indexId}>
              <TableButton
                onClick={() => onButtonClick(item.sentenceId)}
                isPlaying={item.sentenceId === currentSentenceId}
              >
                <p>{item.indexTitle}</p>
              </TableButton>
            </li>
          ))}
        </ul>
      </Navigation>
      <ToggleButton onClick={() => setIsOpen(!isOpen)} isOpen={isOpen}>
        <FaGripLines size={24} />
      </ToggleButton>
    </Container>
  )
}

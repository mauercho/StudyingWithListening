import React from 'react'

import styled from '@emotion/styled'
import { keyframes } from '@emotion/react'

import { FaHeadphones } from 'react-icons/fa'

import theme from '../assets/styles/theme'
import useLongPress from '../hooks/useLongPress'
import usePlayerStore from '../stores/usePlayerStore'

const shimmer = keyframes`
  0% { 
    background-position: -1000px 0; /* 시작 위치 */
  }
  100% {
    background-position: 1000px 0; /* 끝 위치 */
  }
`

const scalePulse = keyframes`
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
  100% {
    transform: scale(1);
  }
`

const Container = styled.li`
  display: flex;
  align-items: top;
  position: relative;
  width: 100%;
  cursor: pointer;
  padding: 10px;
  border-radius: 10px;
  box-sizing: border-box;
  border: 1px solid ${theme.color.grey_dark};
  &:active {
    background-color: ${({ theme }) => theme.color.grey_dark};
  }

  ${({ theme, status }) =>
    status === 'question' &&
    `
      border: 1px solid ${theme.color.primary_dark};
      background-color: ${theme.color.primary_dark};
      opacity: 1;
    `}

  ${({ theme, status, mode }) => {
    if (status === 'question' && mode === 'simple') {
      return `
      border: 1px solid ${theme.color.secondary};
      background-color: ${theme.color.secondary};
    `
    }
  }}

  &::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    /* background: ${({ theme, status }) =>
      status === 'question'
        ? `linear-gradient(
            to right,
            ${theme.color.primary_dark} 8%,
            ${theme.color.primary} 24%,
            ${theme.color.primary_dark} 40%
          )`
        : `linear-gradient(
            to right,
            ${theme.color.white} 8%,
            ${theme.color.grey} 24%,
            ${theme.color.white} 40%
          )`}; */

    background-size: 2000px 100%; /* 쉼머 효과 크기 */
    animation: ${shimmer} 8s infinite linear; /* 쉼머 애니메이션 */
    border-radius: 10px;
    pointer-events: none; /* 클릭 방지 */
    z-index: 1; /* 텍스트 아래로 */
  }
`

const Text = styled.p`
  width: 100%;
  opacity: 1;
  font-size: 16px;
  color: ${({ theme }) => theme.color.black};
  font-weight: ${({ theme }) => theme.font.weight.light};
  line-height: 1.4;
  overflow-wrap: break-word;
  position: relative;
  z-index: 2; /* 텍스트가 쉼머 위로 오게 */
  ${({ theme, status }) =>
    status === 'question' &&
    `
      font-size: ${theme.font.size.lg};
      font-weight: ${theme.font.weight.regular};
      color: ${theme.color.white};
      opacity: 1;
    `}

  ${({ theme, status }) =>
    status === 'question' &&
    `
      font-size: ${theme.font.size.lg};
      font-weight: ${theme.font.weight.regular};
      color: ${theme.color.white};
      opacity: 1;
    `}

  ${({ theme, mode }) =>
    mode === 'simple' &&
    `
      font-size: ${theme.font.size.lg};
      font-weight: ${theme.font.weight.medium};
    `}
`

const IconWrapper = styled.div`
  position: absolute;
  top: -10px;
  right: -5px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
  height: 20px;
  background-color: ${({ theme }) => theme.color.black};
  border-radius: 50%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  z-index: 3;
  animation: ${scalePulse} 2s infinite ease-in-out;
`

export default function Sentence({
  text,
  status,
  index,
  mode = 'detail',
  onShortPress,
  onLongPress,
}) {
  const { currentIndex } = usePlayerStore()
  const { ...longPressHandler } = useLongPress(onLongPress)

  console.log(mode)

  return (
    <Container
      mode={mode}
      status={status}
      onClick={onShortPress}
      {...longPressHandler}
    >
      <Text mode={mode} status={status}>
        {status === 'question' ? 'Q: ' : 'A: '}
        {text}
      </Text>
      {index === currentIndex && (
        <IconWrapper>
          <FaHeadphones color="white" size={16} />
        </IconWrapper>
      )}
    </Container>
  )
}

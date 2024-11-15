import React from 'react'

import styled from '@emotion/styled'
import { FaHeadphones } from 'react-icons/fa'

import useLongPress from '../hooks/useLongPress'
import usePlayerStore from '../stores/usePlayerStore'

const Container = styled.li`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px;
  width: 100%;
  cursor: pointer;
  padding: 10px;
  border-radius: 10px;
  box-sizing: border-box;

  &:active {
    background-color: ${({ theme }) => theme.color.grey_dark};
  }
`

const Text = styled.p`
  width: 100%;
  opacity: 1;
  font-size: 16px;
  color: ${({ theme }) => theme.color.primary_dark};
  font-weight: ${({ theme }) => theme.font.weight.light};
  ${({ theme, status }) =>
    status === 'question' &&
    `
      color: ${theme.color.secondary};
      opacity: 1;
    `}
`

export default function Sentence({
  text,
  status,
  index,
  onShortPress,
  onLongPress,
}) {
  const { currentIndex } = usePlayerStore()

  const { ...longPressHandler } = useLongPress(onLongPress)

  return (
    <Container onClick={onShortPress} {...longPressHandler}>
      {index === currentIndex && <FaHeadphones size={16} />}
      <Text status={status}>{status === 'question' ? 'Q: ' : 'A: '}{text}</Text>
    </Container>
  )
}

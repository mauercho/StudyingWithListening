import React from 'react'
import styled from '@emotion/styled'
import useLongPress from '../hooks/useLongPress'

const Container = styled.li`
  width: 100%;
  cursor: pointer;
  padding: 10px;
  border-radius: 10px;

  &:active {
    background-color: ${({ theme }) => theme.color.grey_dark};
  }
`

const Text = styled.p`
  opacity: 0.6;
  font-size: 16px;
  font-weight: ${({ theme }) => theme.font.weight.light};
  ${({ status }) =>
    status === 'hidden' &&
    `
      opacity: 0.3;
      text-decoration: line-through;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    `}

  ${({ theme, status }) =>
    status === 'playing' &&
    `
      color: ${theme.color.primary_dark};
      opacity: 1;
      font-weight: ${theme.font.weight.regular};
    `}
`

export default function Sentence({ text, status, onShortPress, onLongPress }) {
  const { ...longPressHandler } = useLongPress(onLongPress, onShortPress)

  return (
    <Container {...longPressHandler}>
      <Text status={status}>{text}</Text>
    </Container>
  )
}

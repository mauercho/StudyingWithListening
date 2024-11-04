import React from 'react'
import styled from '@emotion/styled'
import useLongPress from '../hooks/useLongPress'

const Container = styled.div`
  width: 100%;
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
    <Container status={status} {...longPressHandler}>
      {text}
    </Container>
  )
}

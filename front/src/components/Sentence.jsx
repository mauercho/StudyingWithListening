import React from 'react'
import styled from '@emotion/styled'
import useLongPress from '../hooks/useLongPress'
import { colors } from '../assets/styles/colors'

const Container = styled.div`
  width: 100%;
  opcaity: 0.6;
  ${(props) => {
    if (props.status === 'hidden') {
      return `
        opacity: 0.3;
        text-decoration: line-through;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      `
    } else if (props.status === 'playing') {
      return `
        color: ${colors.primary};
        opacity: 1;
      `
    }
  }}
`

export default function Sentence({ text, status, onShortPress, onLongPress }) {
  const { ...longPressHandler } = useLongPress(onLongPress, onShortPress)

  return (
    <Container status={status} {...longPressHandler}>
      {text}
    </Container>
  )
}

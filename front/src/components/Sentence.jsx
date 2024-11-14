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
  opacity: 0.6;
  font-size: 16px;
  font-weight: ${({ theme }) => theme.font.weight.light};
  ${({ status }) =>
    !status &&
    `
      opacity: 0.3;
      text-decoration: line-through;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    `}
  ${({ theme }) =>
    false && // '현재 진행중인 id가 저장된 id와 같다면 == 진행상태면'
    `
      color: ${theme.color.primary_dark};
      opacity: 1;
      font-weight: ${theme.font.weight.regular};
    `}
`

export default function Sentence({
  text,
  status,
  index,
  onShortPress,
  onLongPress,
}) {
  const { setCurrentIndex } = usePlayerStore()

  const handleShortPress = () => {
    onShortPress() // 순서 변경: onShortPress를 먼저 호출
    setCurrentIndex(index)
  }

  const { ...longPressHandler } = useLongPress(onLongPress, handleShortPress)

  return (
    <Container onClick={onShortPress} {...longPressHandler}>
      {false && <FaHeadphones size={16} />}
      <Text status={status}>{text}</Text>
    </Container>
  )
}

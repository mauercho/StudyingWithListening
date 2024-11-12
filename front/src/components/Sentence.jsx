import React from 'react'

import styled from '@emotion/styled'
import { FaHeadphones } from 'react-icons/fa'

import useLongPress from '../hooks/useLongPress'

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
// Q 코드 { color: ${theme.color.secondary} } A 코드 { color: ${theme.color.primary_dark} }

export default function Sentence({ text, status, onShortPress, onLongPress }) {
  const { ...longPressHandler } = useLongPress(onLongPress)
  // TODO: player 코드랑 usePlayerStore 업데이트 되면 헤드폰 조건 변경해주기, API 업데이트 후 스타일 적용하기
  return (
    <Container onClick={onShortPress} {...longPressHandler}>
      {false && <FaHeadphones size={16} />}
      <Text status={status}>{text}</Text>
    </Container>
  )
}

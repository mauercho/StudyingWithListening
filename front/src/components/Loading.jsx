import React from 'react'

import styled from '@emotion/styled'
import { keyframes } from '@emotion/react'

const Container = styled.div`
  width: 100%;
  margin-top: 40px;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  gap: 10px;
`

const LoadingText = styled.p`
  ${({ theme }) =>
    `
      color: ${theme.color.primary};
      font-weight: ${theme.font.weight.regular};
      font-size: ${theme.font.size.base};
    `}
`

const rotation = keyframes`
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
`

const Loader = styled.div`
  width: 16px;
  height: 16px;
  border: 2px solid ${({ theme }) => theme.color.primary}; // theme에서 색상 가져오기
  border-bottom-color: transparent;
  border-radius: 50%;
  display: inline-block;
  box-sizing: border-box;
  animation: ${rotation} 1s linear infinite;
`

export default function Loading({ text = '생성중...' }) {
  return (
    <Container>
      <Loader />
      <LoadingText>{text}</LoadingText>
    </Container>
  )
}

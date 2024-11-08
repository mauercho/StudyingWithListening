import React from 'react'

import styled from '@emotion/styled'
import { Link } from "react-router-dom";

import SimpleLayout from '../layouts/SimpleLayout'

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`

const Error = styled.p`
  color: ${({ theme }) => theme.color.primary};
  font-weight: ${({ theme }) => theme.font.weight.extraBold};
  font-size: ${({ theme }) => theme.font.size['4xl']};
`

const Text = styled.p`
  margin-top: 1rem;
`

const BackHome = styled(Link)`
  margin-top: 1rem;
  text-decoration: none;
`

export default function NotFound({
  error = 404,
  text = '페이지를 찾을 수 없습니다.',
}) {
  return (
    <SimpleLayout>
      <Container>
        <Error>{error}</Error>
        <Text>{text}</Text>
        <BackHome to="/">홈으로 돌아가기</BackHome>
      </Container>
    </SimpleLayout>
  )
}

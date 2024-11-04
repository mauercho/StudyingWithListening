import React from 'react'

import styled from '@emotion/styled'
import { useNavigate } from 'react-router-dom'

import { FaCrow } from 'react-icons/fa6'

const Container = styled.li`
  color: ${({ theme }) => theme.color.primary};
  display: flex;
  align-items: center;
  background-color: ${({ theme }) => theme.color.grey};
  width: 100%;
  height: 100px;
  border-radius: 24px;
  cursor: pointer;
`

const Icon = styled.div`
  width: fit-content;
  margin: 0px 24px 0px 16px;
`

const Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
`

const Title = styled.p`
  font-size: ${({ theme }) => theme.font.size['2xl']};
  font-weight: ${({ theme }) => theme.font.weight.medium};
  color: ${({ theme }) => theme.color.black};
`

const SubTitle = styled.p`
  font-size: ${({ theme }) => theme.font.size['sm']};
  font-weight: ${({ theme }) => theme.font.weight.medium};
  color: ${({ theme }) => theme.color.primary_dark};
`

export default function HomeMenu({
  icon = <FaCrow size={24} />,
  title = '제목',
  subTitle = '부제목을 입력해주세요.',
  url = '/',
  ...props
}) {
  const navigate = useNavigate()

  const handleClick = (url) => {
    navigate(url)
  }

  return (
    <Container {...props} onClick={() => handleClick(url)}>
      <Icon>{icon}</Icon>
      <Content>
        <Title>{title}</Title>
        <SubTitle>{subTitle}</SubTitle>
      </Content>
    </Container>
  )
}

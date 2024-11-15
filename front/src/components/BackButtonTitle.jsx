import React from 'react'

import styled from '@emotion/styled'
import { useNavigate } from 'react-router-dom'

import { FaArrowLeft } from 'react-icons/fa'

const Container = styled.header`
  width: 100%;
  height: 60px;
  padding: 0px 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid ${({ theme }) => theme.color.primary};
  box-sizing: border-box;
`

const Space = styled.div`
  width: 25px;
`

const BackButton = styled.button`
  border: none;
  background: none;
  cursor: pointer;
  width: fit-content;
  height: fit-content;
`

const Title = styled.span`
  font-size: ${({ theme }) => theme.font.size['lg']};
  font-weight: ${({ theme }) => theme.font.weight.bold};
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`

export default function BackButtonTitle({ title = '제목', ...props }) {
  const navigate = useNavigate()

  const handleGoBack = () => {
    navigate(-1)
  }

  return (
    <Container {...props}>
      <BackButton onClick={handleGoBack}>
        <FaArrowLeft size={24} />
      </BackButton>
      <Title>{title}</Title>
      <Space></Space>
    </Container>
  )
}

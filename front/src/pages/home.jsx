import React from 'react'
import styled from '@emotion/styled'

import HomeMenu from '../components/HomeMenu'
import Logo from '../assets/images/logo.svg'
import { FaBookMedical, FaEarListen } from 'react-icons/fa6'

const Container = styled.div`
  padding-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 30px;
  flex: 1;
`

const Text = styled.p`
  font-size: ${({ theme }) => theme.font.size.xl};
  font-weight: ${({ theme }) => theme.font.weight.regular};
`

const Menus = styled.ul`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 10px;
  flex: 1;
`

export default function Home() {
  return (
    <Container>
      <img src={Logo} alt="logo.svg" width={133} height={72} />
      <Text>이동중에도 멈추지 않는 공부</Text>
      <Menus>
        <HomeMenu
          icon={<FaBookMedical size={24} />}
          title="자료 업로드"
          subTitle="학습할 자료를 업로드"
          url="upload"
        />
        <HomeMenu
          icon={<FaEarListen size={24} />}
          title="학습 목록"
          subTitle="학습 목록 보기"
          url="list"
        />
      </Menus>
    </Container>
  )
}

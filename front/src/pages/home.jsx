import React, { useState, useEffect } from 'react'
import styled from '@emotion/styled'

import Lottie from 'react-lottie'
import animationData from '../assets/lottie/personlistening.json'
import Logo from '../assets/images/title.svg'
import FileRegister from '../components/home/FileRegister'
import summariesApi from '../api/summariesApi'
import WordInput from '../components/WordInput'
import List from '../components/home/List'
import UploadModal from '../components/UploadModal'

// Styled components
const Container = styled.div`
  padding-top: 30px;
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 4%;
  flex: 1;
`

const Header = styled.header`
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;

  div {
    position: relative;

    img {
      display: block;
    }

    & > div {
      position: absolute;
      left: 100px;
      top: -30px;
    }
  }
`

const SubTitle = styled.p`
  font-size: ${({ theme }) => theme.font.size.xl};
  font-weight: ${({ theme }) => theme.font.weight.medium};
  text-align: center;
`

const Point = styled.span`
  font-weight: ${({ theme }) => theme.font.weight.bold};
  color: ${({ theme }) => theme.color.primary_dark};
`

const SectionTitle = styled.p`
  font-size: ${({ theme }) => theme.font.size['xl']};
  font-weight: ${({ theme }) => theme.font.weight.medium};
  margin-top: 4px;
`

const SectionSubTitle = styled.p`
  font-size: ${({ theme }) => theme.font.size.sm};
  font-weight: ${({ theme }) => theme.font.weight.regular};
`
// Main component
export default function Home() {
  const [list, setList] = useState([])
  // 새로운 학습 파일 모달
  const [isModalOpen, setIsModalOpen] = useState(false)
  const handleOpenModal = () => setIsModalOpen(true)
  const handleCloseModal = () => setIsModalOpen(false)

  const defaultOptions = {
    loop: true,
    autoplay: true,
    animationData: animationData,
    rendererSettings: {
      preserveAspectRatio: 'xMidYMid slice',
    },
  }

  useEffect(() => {
    const fetchSummaries = async () => {
      try {
        const data = await summariesApi.getSummaries()
        setList(data.slice().reverse().slice(0, 5))
      } catch (error) {
        console.error('Error fetching summaries:', error)
      }
    }

    fetchSummaries()
  }, [])

  return (
    <Container>
      <Header>
        <div>
          <img src={Logo} alt="title.svg" />
          <Lottie options={defaultOptions} height={100} width={120} />
        </div>
      </Header>
      <SubTitle>
        <Point>듣</Point>기만 해도 <Point>공</Point>부가 되는
      </SubTitle>
      <FileRegister onClick={handleOpenModal} />
      <UploadModal
        isOpen={isModalOpen}
        onClose={handleCloseModal}
      ></UploadModal>
      <div>
        <div>
          <SectionSubTitle>학습 자료를 준비하지 못하셨나요?</SectionSubTitle>
          <SectionTitle>
            <Point>단어 입력</Point>으로 학습하러 가기
          </SectionTitle>
        </div>
        <WordInput />
      </div>
      <List list={list} />
    </Container>
  )
}

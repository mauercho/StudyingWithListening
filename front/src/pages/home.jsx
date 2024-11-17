import React, { useState, useEffect } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import styled from '@emotion/styled'

import Lottie from 'react-lottie'
import animationData from '../assets/lottie/personlistening.json'
import Logo from '../assets/images/title.svg'
import FileRegister from '../components/FileRegister'
import summariesApi from '../api/summariesApi'
import { FaAngleRight } from 'react-icons/fa6'
import WordInput from '../components/WordInput'

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

const ListTitle = styled.p`
  display: flex;
  justify-content: space-between;
`

const ListMoveButton = styled(Link)`
  text-decoration: none;
  display: flex;
  align-items: center;
  font-size: ${({ theme }) => theme.font.size.xs};
  font-weight: ${({ theme }) => theme.font.weight.bold};
  color: ${({ theme }) => theme.color.accent};
`

const ListWrapper = styled.ul`
  display: flex;
  overflow-x: scroll;
  gap: 20px;
  padding-bottom: 10px;
  margin-top: 10px;
`

const ListItem = styled.li`
  min-width: 35%;
  padding: 10px;
  border: 1px solid ${({ theme }) => theme.color.primary_dark};
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100px;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);

  p {
    font-size: ${({ theme }) => theme.font.size.xs};
    font-weight: ${({ theme }) => theme.font.weight.medium};
    &:first-of-type {
      font-size: ${({ theme }) => theme.font.size.base};
      display: block;
      height: 32px;
    }

    &:last-of-type {
      font-size: ${({ theme }) => theme.font.size.sm};
    }
  }
`

// Helper function
const formatDate = (dateString) =>
  new Date(dateString).toISOString().split('T')[0]

// Main component
export default function Home() {
  const [list, setList] = useState([])
  const navigate = useNavigate()

  const defaultOptions = {
    loop: true,
    autoplay: true,
    animationData: animationData,
    rendererSettings: {
      preserveAspectRatio: 'xMidYMid slice',
    },
  }

  const handleLinkClick = (summariesId) => navigate(`/detail/${summariesId}`)

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
        <Point>듣</Point>는 것만으로 '암기'가 되는 <Point>공</Point>부
      </SubTitle>
      <FileRegister />
      <div>
        <ListTitle>
          <div>
            <SectionSubTitle>우리 아직 다 못 외웠잖아요?</SectionSubTitle>
            <SectionTitle>
              <Point>복습</Point>하러 가기
            </SectionTitle>
          </div>
          <ListMoveButton to="list">
            더보기 <FaAngleRight />
          </ListMoveButton>
        </ListTitle>
        <ListWrapper>
          {list.map((v) => {
            return (
              <ListItem key={v.id} onClick={() => handleLinkClick(v.id)}>
                <p>{v.title || '제목 없음'}</p>
                <p>{formatDate(v.modifiedAt)}</p>
                <p>
                  <Point>암기 달성:</Point> {Math.floor(Math.random() * 30)} /
                  52
                </p>
              </ListItem>
            )
          })}
        </ListWrapper>
      </div>
      <div>
        <div>
          <SectionSubTitle>학습 자료를 준비하지 못하셨나요?</SectionSubTitle>
          <SectionTitle>
            <Point>단어 입력</Point>으로 학습하러 가기
          </SectionTitle>
        </div>
        <WordInput />
      </div>
    </Container>
  )
}

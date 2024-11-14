import React, { useState, useEffect } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import styled from '@emotion/styled'

import Logo from '../assets/images/logo.svg'
import { FaBookMedical, FaEarListen } from 'react-icons/fa6'

import FileInput from '../components/FileInput'
import summariesApi from '../api/summariesApi'

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

const ListTitle = styled(Link)`
  text-decoration: none;
  color: ${({ theme }) => theme.color.black};
  width: 100%;
  font-size: ${({ theme }) => theme.font.size.xl};
  font-weight: ${({ theme }) => theme.font.weight.medium};
`

const ListContainer = styled.ul`
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  margin-top: 10px;
  width: 100%;
  overflow-y: auto;
  border-top: 1px solid ${({ theme }) => theme.color.primary_dark};
`

const Item = styled.li`
  height: 72px;
  padding: 0px 10px;
  display: flex;
  align-items: center;
  width: 100%;
  cursor: pointer;
  box-sizing: border-box;
  border-bottom: 1px solid ${({ theme }) => theme.color.primary_dark};

  &:hover {
    background-color: ${({ theme }) => theme.color.grey};
  }
`

export default function Home() {
  const [list, setList] = useState([])
  const navigate = useNavigate()

  const handleLinkClick = (summariesId) => {
    navigate(`/detail/${summariesId}`)
  }

  useEffect(() => {
    const fetchSummaries = async () => {
      try {
        const data = await summariesApi.getSummaries()
        console.log('1,2, 3,4,')
        setList(data)
      } catch (error) {
        console.error('Error fetching user:', error)
      }
    }

    fetchSummaries()
  }, [])

  return (
    <Container>
      <img src={Logo} alt="logo.svg" width={133} height={72} />
      <Text>듣는 것만으로 암기가 되는 학습</Text>
      <FileInput />
      <ListTitle to={'list'}>이전 학습</ListTitle>
      <ListContainer>
        {list.map((v, index) => {
          return (
            <Item key={index} onClick={() => handleLinkClick(v.id)}>
              {v.title ? v.title : '제목 없음'}
            </Item>
          )
        })}
      </ListContainer>
    </Container>
  )
}

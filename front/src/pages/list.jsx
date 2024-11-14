import React, { useEffect, useState } from 'react'

import styled from '@emotion/styled'
import { useNavigate } from 'react-router-dom'

import summariesApi from '../api/summariesApi'

const Container = styled.ul`
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  gap: 10px;
  margin-top: 10px;
  width: 100%;
  overflow-y: auto;
`

const Item = styled.li`
  height: 72px;
  padding: 0px 10px;
  display: flex;
  align-items: center;
  width: 100%;
  cursor: pointer;
  border-radius: 8px;
  box-sizing: border-box;

  &:hover {
    background-color: ${({ theme }) => theme.color.grey};
  }
`

export default function List() {
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
      {list.map((v, index) => {
        return (
          <Item key={index} onClick={() => handleLinkClick(v.id)}>
            {v.title ? v.title : '제목 없음'}
          </Item>
        )
      })}
    </Container>
  )
}

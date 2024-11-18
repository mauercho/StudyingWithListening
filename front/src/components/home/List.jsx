import React from 'react'
import { useNavigate, Link } from 'react-router-dom'

import styled from '@emotion/styled'

import { FaAngleRight } from 'react-icons/fa6'

const SectionSubTitle = styled.p`
  font-size: ${({ theme }) => theme.font.size.sm};
  font-weight: ${({ theme }) => theme.font.weight.regular};
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
      display: inline-block;
      min-height: 32px;
      overflow: hidden; // 넘치는 텍스트 숨김
      text-overflow: ellipsis; // ...으로 표시
      white-space: nowrap; // 한 줄로 표시
    }

    &:last-of-type {
      font-size: ${({ theme }) => theme.font.size.sm};
    }
  }
`;


// Helper function
const formatDate = (dateString) =>
  new Date(dateString).toISOString().split('T')[0]

export default function List({ list, ...props }) {
  const navigate = useNavigate()
  const handleLinkClick = (summariesId) => navigate(`/detail/${summariesId}`)

  return (
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
                <Point>암기 달성:</Point> {Math.floor(Math.random() * 30)} / 52
              </p>
            </ListItem>
          )
        })}
      </ListWrapper>
    </div>
  )
}

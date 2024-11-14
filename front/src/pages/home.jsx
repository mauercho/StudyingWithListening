import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import styled from '@emotion/styled';

import Logo from '../assets/images/logo.svg';
import FileInput from '../components/FileInput';
import summariesApi from '../api/summariesApi';

import { FaAngleRight } from 'react-icons/fa6';

const Container = styled.div`
  padding-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 30px;
  flex: 1;
`;

const Text = styled.p`
  font-size: ${({ theme }) => theme.font.size.xl};
  font-weight: ${({ theme }) => theme.font.weight.regular};
`;

const ListTitle = styled.p`
  color: ${({ theme }) => theme.color.black};
  width: 100%;
  font-size: ${({ theme }) => theme.font.size.xl};
  font-weight: ${({ theme }) => theme.font.weight.medium};
  text-align: center;
`;

const ListContainer = styled.ul`
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  width: 100%;
  height: 250px;
  border-top: 1px solid ${({ theme }) => theme.color.primary_dark};
  border-bottom: 1px solid ${({ theme }) => theme.color.primary_dark};
`;

const EmptyContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 250px;
  border: 1px solid ${({ theme }) => theme.color.primary_dark};
`;

const Item = styled.li`
  height: 50px;
  padding: 10px;
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 100%;
  cursor: pointer;
  box-sizing: border-box;
  border-bottom: 1px solid ${({ theme }) => theme.color.primary_dark};

  &:hover {
    background-color: ${({ theme }) => theme.color.grey};
  }
`;

const ItemTitle = styled.span`
  font-size: ${({ theme }) => theme.font.size.sm};
  font-weight: ${({ theme }) => theme.font.weight.regular};
  width: 65%;
  margin-right: 5%;
`;

const ItemNum = styled.span`
  width: 10%;
  font-size: ${({ theme }) => theme.font.size.xs};
  color: ${({ theme }) => theme.color.primary};
`;

const ItemDate = styled.span`
  width: 20%;
  font-size: ${({ theme }) => theme.font.size.xs};
  color: ${({ theme }) => theme.color.primary};
`;

const ListMoveButton = styled(Link)`
  text-decoration: none;
  display: flex;
  align-items: center;
  font-size: ${({ theme }) => theme.font.size.sm};
  font-weight: ${({ theme }) => theme.font.weight.regular};
  color: ${({ theme }) => theme.color.accent};
`;

export default function Home() {
  const [list, setList] = useState([]);
  const navigate = useNavigate();

  const handleLinkClick = (summariesId) => {
    navigate(`/detail/${summariesId}`);
  };

  useEffect(() => {
    const fetchSummaries = async () => {
      try {
        const data = await summariesApi.getSummaries();
        setList(data);
      } catch (error) {
        console.error('Error fetching summaries:', error);
      }
    };

    fetchSummaries();
  }, []);

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toISOString().split('T')[0]; // YYYY-MM-DD 형식으로 반환
  };

  return (
    <Container>
      <img src={Logo} alt="logo.svg" width={133} height={72} />
      <Text>듣는 것만으로 암기가 되는 학습</Text>
      <FileInput />
      <ListTitle>최근 학습 목록</ListTitle>
      {list.length ? (
        <ListContainer>
          {list.map((v, index) => (
            <Item key={index} onClick={() => handleLinkClick(v.id)}>
              <ItemNum>{v.id}</ItemNum>
              <ItemTitle>{v.title ? v.title : '제목 없음'}</ItemTitle>
              <ItemDate>{formatDate(v.modifiedAt)}</ItemDate>
            </Item>
          ))}
        </ListContainer>
      ) : (
        <EmptyContainer>최근 학습한 목록이 없어요!</EmptyContainer>
      )}

      <ListMoveButton to={'list'}>
        내 학습 목록 <FaAngleRight />
      </ListMoveButton>
    </Container>
  );
}

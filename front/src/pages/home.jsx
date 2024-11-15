import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import styled from '@emotion/styled';

import Logo from '../assets/images/logo.svg';
import FileInput from '../components/FileInput';
import summariesApi from '../api/summariesApi';
import { FaAngleRight } from 'react-icons/fa6';

// 공통 스타일 변수
const borderColor = ({ theme }) => theme.color.primary_dark;
const textColor = ({ theme }) => theme.color.primary;
const fontSize = ({ theme }) => theme.font.size;
const fontWeight = ({ theme }) => theme.font.weight;

// Styled components
const Container = styled.div`
  padding-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 30px;
  flex: 1;
`;

const Text = styled.p`
  font-size: ${fontSize.xl};
  font-weight: ${fontWeight.regular};
`;

const ListTitle = styled.p`
  color: ${({ theme }) => theme.color.black};
  width: 100%;
  font-size: ${fontSize.xl};
  font-weight: ${fontWeight.medium};
  text-align: center;
`;

const ListWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 250px;
  border-top: 1px solid ${borderColor};
  border-bottom: 1px solid ${borderColor};
`;

const ListContainer = styled.ul`
  ${ListWrapper}
  flex-direction: column;
`;

const Item = styled.li`
  display: flex;
  align-items: center;
  height: 50px;
  padding: 10px;
  width: 100%;
  cursor: pointer;
  border-bottom: 1px solid ${borderColor};

  &:hover {
    background-color: ${({ theme }) => theme.color.grey};
  }
`;

const ItemText = styled.span`
  font-size: ${fontSize.sm};
  font-weight: ${fontWeight.regular};
`;

const ItemNum = styled(ItemText)`
  width: 10%;
  color: ${textColor};
  font-size: ${fontSize.xs};
`;

const ItemTitle = styled(ItemText)`
  width: 65%;
  margin-right: 5%;
`;

const ItemDate = styled(ItemText)`
  width: 20%;
  color: ${textColor};
  font-size: ${fontSize.xs};
`;

const ListMoveButton = styled(Link)`
  text-decoration: none;
  display: flex;
  align-items: center;
  font-size: ${fontSize.sm};
  font-weight: ${fontWeight.regular};
  color: ${({ theme }) => theme.color.accent};
`;

// Helper function
const formatDate = (dateString) => new Date(dateString).toISOString().split('T')[0];

// Main component
export default function Home() {
  const [list, setList] = useState([]);
  const navigate = useNavigate();

  const handleLinkClick = (summariesId) => navigate(`/detail/${summariesId}`);

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

  return (
    <Container>
      <img src={Logo} alt="logo.png" width={133} height={72} />
      <Text>듣는 것만으로 암기가 되는 학습</Text>
      <FileInput />
      <ListTitle>최근 학습 목록</ListTitle>

      {list.length ? (
        <ListContainer>
          {list.map((v) => (
            <Item key={v.id} onClick={() => handleLinkClick(v.id)}>
              <ItemNum>{v.id}</ItemNum>
              <ItemTitle>{v.title || '제목 없음'}</ItemTitle>
              <ItemDate>{formatDate(v.modifiedAt)}</ItemDate>
            </Item>
          ))}
        </ListContainer>
      ) : (
        <ListWrapper>최근 학습한 목록이 없어요!</ListWrapper>
      )}

      <ListMoveButton to="list">
        내 학습 목록 <FaAngleRight />
      </ListMoveButton>
    </Container>
  );
}

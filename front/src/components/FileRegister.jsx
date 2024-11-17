import React from 'react';
import styled from '@emotion/styled';
import Lottie from 'react-lottie';
import animationData from '../assets/lottie/headthinking.json';

const Container = styled.div`
  width: 100%;
  height: 8rem;
  display: flex;
  font-weight: ${({ theme }) => theme.font.weight.regular};
  font-size: ${({ theme }) => theme.font.size.base};
  cursor: pointer;
  border-radius: 8px;
  border: 2px solid ${({ theme }) => theme.color.primary_dark};

  section {
    &:first-of-type {
      width: 60%;
      display: flex;
      flex-direction: column;
      justify-content: center;
      padding: 0 1rem;

      div {
        margin-top: 1rem;
        width: 6rem;
        height: 2rem;
        font-size: ${({ theme }) => theme.font.size.sm};
        color: ${({ theme }) => theme.color.white};
        background-color: ${({ theme }) => theme.color.primary_dark};
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 8px;
        cursor: pointer;

        &:hover {
          background-color: ${({ theme }) => theme.color.primary};
        }
      }
    }

    &:last-of-type {
      width: 40%;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
`;

export default function FileRegister() {
  const defaultOptions = {
    loop: true,
    autoplay: true,
    animationData: animationData,
    rendererSettings: {
      preserveAspectRatio: 'xMidYMid slice',
    },
  };

  return (
    <Container>
      <section>
        <p>새로운 학습 파일을 업로드</p>
        <p>하고 암기를 하러 가볼까요?</p>
        <div>시작하기!</div>
      </section>
      <section>
        <Lottie options={defaultOptions} height={100} width={120} />
      </section>
    </Container>
  );
}

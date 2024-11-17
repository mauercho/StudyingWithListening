import React, { useState, useEffect } from 'react';
import styled from '@emotion/styled';

const Container = styled.div`
  width: 100%;
  height: 30px;
  background-color: ${({ theme }) => theme.color.grey};
  border-radius: 15px;
  overflow: hidden;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
`;

const Filler = styled.div`
  height: 100%;
  background-color: ${({ theme }) => theme.color.primary};
  transition: width 0.5s ease-in-out;
  width: ${({ progress }) => `${progress}%`};
`;

const ProgressText = styled.div`
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  font-size: ${({ theme }) => theme.font.size.sm};
  font-weight: ${({ theme }) => theme.font.weight.medium};
  color: ${({ theme }) => theme.color.black};
  line-height: 30px;
`;

export default function ProgressBar({ serverResponse }) {
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    let interval;

    if (serverResponse) {
      setProgress(100);
      return;
    }

    if (progress < 100) {
      interval = setInterval(() => {
        setProgress((prevProgress) => Math.min(prevProgress + 2, 100)); // 초당 2% 증가
      }, 1000);
    }

    return () => clearInterval(interval);
  }, [progress, serverResponse]);

  useEffect(() => {
    if (serverResponse) {
      setProgress(100);
    }
  }, [serverResponse]);

  return (
    <Container>
      <Filler progress={progress} />
      <ProgressText>{progress}%</ProgressText>
    </Container>
  );
}

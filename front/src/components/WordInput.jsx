import React, { useState } from "react";
import styled from "@emotion/styled";
import WordModal from "./WordModal";

const Container = styled.div`
  height: 50px;
  display: flex;
  justify-content: space-between;
  padding: 0px 10px;
  align-items: center;
  background-color: ${({ theme }) => theme?.color?.primary_light || "#f0f0f0"};
  border-radius: 8px;
  margin-top: 10px;
`;

const Input = styled.input`
  width: 70%;
  border: none;
  height: 30px;

  &:focus {
    outline: none;
  }
`;

const Button = styled.button`
  width: 20%;
  border-radius: 4px;
  height: 30px;
  border: none;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${({ theme }) => theme?.color?.primary_dark || "#333"};
  color: ${({ theme }) => theme?.color?.white || "#fff"};
  font-weight: ${({ theme }) => theme?.font?.weight?.bold || "bold"};
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  cursor: pointer;
`;

export default function WordInput() {
  const [text, setText] = useState(""); // 초기값 설정
  const [isWordModalOpen, setIsWordModalOpen] = useState(false);

  const handleWordOpenModal = () => {
    if (!text.trim()) {
      alert("단어를 입력하세요!");
      return;
    }
    setIsWordModalOpen(true);
  };

  const handleWordCloseModal = () => setIsWordModalOpen(false);

  return (
    <Container>
      <Input
        aria-label="단어를 입력하세요"
        value={text}
        onChange={(e) => setText(e.target.value)}
      />
      <Button onClick={handleWordOpenModal} aria-label="단어 암기 버튼">
        암기
      </Button>
      <WordModal
        isOpen={isWordModalOpen}
        onClose={handleWordCloseModal}
        text={text}
      />
    </Container>
  );
}

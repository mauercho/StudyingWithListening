import React from 'react'

import styled from '@emotion/styled'

const Container = styled.div`
  height: 50px;
  display: flex;
  justify-content: space-between;
  padding: 0px 10px 0px 10px;
  align-items: center;
  background-color: ${({ theme }) => theme.color.primary_light};
  border-radius: 8px;
  margin-top: 10px;
`

const Input = styled.input`
  width: 70%;
  border: none;
  height: 30px;

    &:focus {
    outline: none;
  }

`

const Button = styled.button`
  width: 20%;
  border-radius: 4px;
  height: 30px;
  border: none;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${({ theme }) => theme.color.primary_dark};
  color: ${({ theme }) => theme.color.white};
  font-weight: ${({ theme }) => theme.font.weight.bold};
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  cursor: pointer;
`

export default function WordInput() {
  return (
    <Container>
      <Input aria-label="word_input" />
      <Button>암기</Button>
    </Container>
  )
}

// SimpleLayout.js
import React from 'react'
import styled from '@emotion/styled'

const Container = styled.div`
  margin: auto;
  max-width: 768px;
  width: 100%;
  min-width: 320px;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${({ theme }) => theme.color.white};
`

export default function SimpleLayout({ children }) {
  return <Container>{children}</Container>
}

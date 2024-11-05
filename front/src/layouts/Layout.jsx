import React from 'react'

import { Outlet } from 'react-router-dom'
import styled from '@emotion/styled'

const Container = styled.div`
  margin: auto;
  padding: 0px 10px;
  max-width: 768px;
  min-width: 320px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: ${({theme}) => theme.color.white};
`

const Player = styled.div`
  height: 65px;
`

export default function Layout() {
  return (
    <Container>
      <Outlet />
      <Player></Player>
    </Container>
  )
}

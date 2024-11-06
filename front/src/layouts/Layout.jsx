import React from 'react'

import { Outlet } from 'react-router-dom'
import styled from '@emotion/styled'

import Player from '../components/Player'

const Container = styled.div`
  margin: auto;
  max-width: 768px;
  width: 100%;
  min-width: 320px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.color.white};
`

const Main = styled.main`
  padding: 0px 10px;
  flex: 1;
  display: flex;
`

const PlayerContainer = styled.div`
  width: 100%;
`

export default function Layout() {
  return (
    <Container>
      <Main>
        <Outlet />
      </Main>
      <PlayerContainer>
        <Player />
      </PlayerContainer>
    </Container>
  )
}

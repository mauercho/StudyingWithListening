import React from 'react'

import { Outlet } from 'react-router-dom'
import styled from '@emotion/styled'

import Player from '../components/Player'

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

export default function Layout() {
  return (
    <Container>
      <Outlet />
      <Player />
    </Container>
  )
}

import React from 'react'
import styled from '@emotion/styled'
import { Outlet, useLocation, matchPath } from 'react-router-dom'

import routes from '../routes'
import BackButtonTitle from '../components/BackButtonTitle'
import Player from '../components/Player'

const Container = styled.main`
  margin: auto;
  max-width: 768px;
  width: 100%;
  min-width: 320px;
  height: 100vh;
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.color.white};
`

const Main = styled.div`
  overflow-y: auto;
  box-sizing: border-box;
`
const PlayerContainer = styled.div`
  width: 100%;
`

export default function TitleLayout() {
  const location = useLocation()

  const currentRoute = routes.find((route) =>
    matchPath(route.path, location.pathname)
  )
  const title = currentRoute ? currentRoute.title : ''

  return (
    <Container>
      <BackButtonTitle title={title} />
      <Main>
        <Outlet />
      </Main>
      <PlayerContainer>
        <Player />
      </PlayerContainer>
    </Container>
  )
}

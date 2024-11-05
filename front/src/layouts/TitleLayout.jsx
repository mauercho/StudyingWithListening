import React from 'react';
import styled from '@emotion/styled';
import { Outlet, useLocation } from 'react-router-dom';

import routes from '../routes';
import BackButtonTitle from '../components/BackButtonTitle';
import Player from '../components/Player'

const Container = styled.main`
  margin: auto;
  max-width: 768px;
  min-width: 320px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: ${({theme}) => theme.color.white};
`;

const Main = styled.div`
  padding: 0px 10px;
  flex: 1;
  overflow-y: auto;
`;


export default function TitleLayout() {
  const location = useLocation();
  const currentRoute = routes.find((route) => route.path === location.pathname);
  const title = currentRoute ? currentRoute.title : '';

  return (
    <Container>
      <BackButtonTitle title={title} />
      <Main>
        <Outlet />
      </Main>
      <Player />
    </Container>
  );
}

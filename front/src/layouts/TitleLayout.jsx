import React from 'react';
import styled from '@emotion/styled';
import { Outlet, useLocation } from 'react-router-dom';

import routes from '../routes';
import BackButtonTitle from '../components/BackButtonTitle';
import Player from '../components/Player';

const Container = styled.main`
  margin: auto;
  max-width: 768px;
  min-width: 320px;
  min-height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
  background-color: ${({ theme }) => theme.color.white};
`;

const Main = styled.div`
  padding: 0px 10px;
  flex: 1; /* 남은 공간을 차지하도록 설정 */
  overflow-y: auto;
  display: flex;
`;

const FixedPlayer = styled(Player)`
  position: absolute; /* Container 내부 하단에 고정 */
  bottom: 0;
  left: 0;
  width: 100%;
  max-width: 768px;
  background-color: ${({ theme }) => theme.color.white};
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
      <FixedPlayer />
    </Container>
  );
}

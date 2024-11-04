import React from 'react'

import { Outlet } from 'react-router-dom'
import styled from '@emotion/styled'

const Container = styled.div`
  margin: auto;
  padding: 0px 10px;
  max-width: 768px;
  min-width: 320px;
  min-height: 100vh;

  @media (min-width: 320px) and (max-width: 480px) {
    max-width: 100%;
  }

  @media (min-width: 481px) and (max-width: 768px) {
    /* 스타일 설정 */
  }
`

export default function Layout() {
  return (
    <div>
      <Container>
        <Outlet />
      </Container>
    </div>
  )
}

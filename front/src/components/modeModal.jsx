import React from 'react'

import styled from '@emotion/styled'

const Container = styled.div`
  display: flex;
  flex-direction: column;
  box-shadow: 0 -4px 6px rgba(0, 0, 0, 0.2), 0 4px 6px rgba(0, 0, 0, 0.2);
`

const Title = styled.nav`
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`

export default function modeModal() {
  return (
    <Container>
      <div></div>
    </Container>
  )
}

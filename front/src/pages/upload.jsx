import React from 'react'

import styled from '@emotion/styled'

import FileInput from '../components/FileInput'

const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex: 1;
`

export default function Upload() {
  return (
    <Container>
      <FileInput />
    </Container>
  )
}

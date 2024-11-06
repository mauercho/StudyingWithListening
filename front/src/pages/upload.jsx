import React from 'react'

import styled from '@emotion/styled'

import FileInput from '../components/FileInput'

const Container = styled.div`
  display: flex;
  align-items: center;
  height: 100%;
  justify-content: center;
`

export default function Upload() {
  return (
    <Container>
      <FileInput />
    </Container>
  )
}

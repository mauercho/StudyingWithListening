import React from 'react'

import styled from '@emotion/styled'

const Container = styled.nav`
  display: flex;
  flex-direction: row-reverse;
`

const Menu = styled.button`
  width: 64px;
  margin-top: 30px;
  padding: 4px 10px;
  border-radius: 10px 10px 0 0;
  border: 1px solid ${({ theme }) => theme.color.primary_dark};
  border-bottom: 0;
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  ${({ theme, isThisMode }) =>
    isThisMode &&
    `
      background: ${theme.color.primary_dark};
      color: ${theme.color.primary_light};
    `}
`

export default function BookmarkMenu({
  summaryMode = 'normal',
  onButtonClick,
  menuItems = [
    {
      title: '일반',
      mode: 'normal',
    },
  ],
}) {
  return (
    <Container>
      <Menu
        onClick={() => onButtonClick('keyword')}
        isThisMode={summaryMode === 'keyword'}
      >
        키워드
      </Menu>
      <Menu
        onClick={() => onButtonClick('detail')}
        isThisMode={summaryMode === 'detail'}
      >
        상세
      </Menu>
      <Menu
        onClick={() => onButtonClick('normal')}
        isThisMode={summaryMode === 'normal'}
      >
        일반
      </Menu>
    </Container>
  )
}

import React from 'react'

import styled from '@emotion/styled'

const Container = styled.nav`
  display: flex;
  flex-direction: row-reverse;
`

const Menu = styled.button`
  width: 64px;
  padding: 4px 10px;
  border-radius: 10px 10px 0 0;
  border: 1px solid ${({ theme }) => theme.color.primary_dark};
  border-bottom: 0;
  box-shadow: 4px 0px 4px rgba(0, 0, 0, 0.25);
  cursor: pointer;
  transition: background-color 0.3s, color 0.3s;
  ${({ theme, isActive }) =>
    isActive &&
    `
      background: ${theme.color.primary_dark};
      color: ${theme.color.primary_light};
    `}
`

export default function BookmarkMenu({
  summaryMode = 'normal',
  onButtonClick,
  menuItems,
}) {
  return (
    <Container>
      {menuItems.map(({ title, mode }) => (
        <Menu
          key={mode}
          isActive={summaryMode === mode}
          onClick={() => onButtonClick(mode)}
        >
          {title}
        </Menu>
      ))}
    </Container>
  )
}

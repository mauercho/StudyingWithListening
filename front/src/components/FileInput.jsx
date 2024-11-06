import React, { useRef, useState } from 'react'

import styled from '@emotion/styled'
import { FaPlus, FaArrowUp } from 'react-icons/fa6'

import PopUpMenu from './PopUpMenu'

const Container = styled.div`
  width: 100%;
  height: 72px;
  background-color: ${({ theme }) => theme.color.grey};
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 40px;
`

const Button = styled.button`
  cursor: pointer;
  display: flex;
  width: 30px;
  height: 30px;
  justify-content: center;
  align-items: center;
  border: none;
  border-radius: 50%;
  margin: 0px 10px;
`

const AddButton = styled(Button)`
  background-color: ${({ theme }) => theme.color.white};
  color: ${({ theme }) => theme.color.primary};
`

const UploadButton = styled(Button)`
  background-color: ${({ theme }) => theme.color.primary_dark};
  color: ${({ theme }) => theme.color.white};
`

const Input = styled.input`
  flex: 1;
  border: none;
  height: 30px;
  border-radius: 8px;
  outline: none;
  padding: 0px 10px;
  background-color: ${({ theme, url }) =>
    url ? theme.color.white : theme.color.grey};
  font-size: ${({ theme }) => theme.font.size.xs};
  font-weight: ${({ theme }) => theme.font.weight.regular};
`

export default function FileInput() {
  const [type, setType] = useState('url')
  const [isMenuOpen, setIsMenuOpen] = useState(false)
  const buttonRef = useRef(null)

  const handleAddButtonClick = () => setIsMenuOpen((prev) => !prev)

  const items = [
    { text: 'PDF', onClick: () => setType('PDF') },
    { text: 'IMAGE', onClick: () => setType('IMAGE') },
    { text: 'URL', onClick: () => setType('URL') },
  ]

  return (
    <Container>
      <AddButton ref={buttonRef} onClick={handleAddButtonClick}>
        <FaPlus size={16} />
      </AddButton>
      <PopUpMenu
        triggerRef={buttonRef}
        isOpen={isMenuOpen}
        onClose={() => setIsMenuOpen(false)}
        items={items}
      />

      <Input
        aria-label="fileInput"
        url={type === 'URL'}
        disabled={type !== 'URL'}
        placeholder={type === 'URL' ? 'URL을 입력해주세요.' : ''}
      />
      <UploadButton>
        <FaArrowUp size={16} />
      </UploadButton>
    </Container>
  )
}
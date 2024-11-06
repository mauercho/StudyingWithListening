import React, { useRef, useState } from 'react';
import styled from '@emotion/styled';
import { FaPlus, FaArrowUp } from 'react-icons/fa6';

import PopUpMenu from './PopUpMenu';
import uploadApi from '../api/uploadApi';

const Container = styled.div`
  width: 100%;
  height: 72px;
  background-color: ${({ theme }) => theme.color.grey};
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-radius: 40px;
`;

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
`;

const AddButton = styled(Button)`
  background-color: ${({ theme }) => theme.color.white};
  color: ${({ theme }) => theme.color.primary};
`;

const UploadButton = styled(Button)`
  background-color: ${({ theme }) => theme.color.primary_dark};
  color: ${({ theme }) => theme.color.white};
`;

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
`;

export default function FileInput() {
  const [type, setType] = useState('PDF');
  const [data, setData] = useState('');
  const [name, setName] = useState('왼쪽 + 버튼을 눌러서 원하시는 학습 자료를 선택해보세요!');
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const buttonRef = useRef(null);
  const inputRef = useRef(null);

  const handleAddButtonClick = () => setIsMenuOpen((prev) => !prev);

  const handleInput = (inputType) => {
    setType(inputType);
    setData('');
    setName(inputType === 'URL' ? '' : ''); // URL 타입의 경우 입력 초기화

    if (inputType === 'PDF' || inputType === 'IMAGE') {
      inputRef.current.accept = inputType === 'PDF' ? 'application/pdf' : 'image/*';
      inputRef.current.click();
    }
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      setName(file.name);
      setData(file);
    }
  };

  const handleUpload = async () => {
    const formData = new FormData();

    formData.append('type', type);
    if (type === 'URL') {
      formData.append('url', data);
    } else {
      formData.append('uploadContent', data);
    }

    try {
      const result = await uploadApi.post(formData);
      console.log('Upload successful:', result);
    } catch (error) {
      console.error('Upload failed:', error);
    }
  };

  const items = [
    { text: 'PDF', onClick: () => handleInput('PDF') },
    { text: 'IMAGE', onClick: () => handleInput('IMAGE') },
    { text: 'URL', onClick: () => handleInput('URL') },
  ];

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
        value={type === 'URL' ? data : name}
        onChange={(e) => {
          if (type === 'URL') {
            setData(e.target.value);
            setName(e.target.value);
          }
        }}
      />
      <input
        type="file"
        ref={inputRef}
        style={{ display: 'none' }}
        onChange={handleFileChange}
      />
      <UploadButton onClick={handleUpload}>
        <FaArrowUp size={16} />
      </UploadButton>
    </Container>
  );
}

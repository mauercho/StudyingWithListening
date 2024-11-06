import React, { useState, useEffect, useRef } from 'react';
import styled from '@emotion/styled';

const Container = styled.ul`
  position: absolute;
  top: ${({ position }) => position.top}px;
  ${({ location, position }) =>
    location === 'r' ? `left: ${position.left}px;` : `right: ${position.left}px;`}
  background-color: ${({ theme }) => theme.color.white};
  border: 1px solid ${({ theme }) => theme.color.primary};
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 100;
`;

const Item = styled.li`
  cursor: pointer;
  padding: 8px 12px;
  border-radius: 4px;

  &:hover {
    color: ${({ theme }) => theme.color.primary_dark};
  }
`;

function PopUpMenuItem({ onClick, onClose, children }) {
  const handleClick = () => {
    onClick();
    onClose();
  };

  return <Item onClick={handleClick}>{children}</Item>;
}

export default function PopUpMenu({
  triggerRef,
  isOpen,
  onClose,
  items = [],
  location = 'r',
}) {
  const [position, setPosition] = useState({ top: 0, left: 0 });
  const menuRef = useRef(null);

  const updatePosition = () => {
    if (triggerRef.current && menuRef.current) {
      const rect = triggerRef.current.getBoundingClientRect();
      const top = rect.top + window.scrollY - menuRef.current.offsetHeight;
      const left =
        location === 'r'
          ? rect.right + window.scrollX
          : window.innerWidth - rect.left - menuRef.current.offsetWidth;

      setPosition({ top, left });
    }
  };

  useEffect(() => {
    if (isOpen) {
      updatePosition(); // 팝업이 열릴 때 위치를 업데이트
      window.addEventListener('resize', updatePosition); // 창 크기 변경 시 위치 업데이트
      return () => window.removeEventListener('resize', updatePosition);
    }
  }, [isOpen, triggerRef, location]);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (
        menuRef.current &&
        !menuRef.current.contains(event.target) &&
        triggerRef.current &&
        !triggerRef.current.contains(event.target)
      ) {
        onClose();
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, [onClose, triggerRef]);

  if (!isOpen) return null;

  return (
    <Container ref={menuRef} position={position} location={location}>
      {items.map((item, index) => (
        <PopUpMenuItem key={index} onClick={item.onClick} onClose={onClose}>
          {item.text}
        </PopUpMenuItem>
      ))}
    </Container>
  );
}

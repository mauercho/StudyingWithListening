import useLongPress from '../hooks/useLongPress'
import React from 'react'

export default function Sentence({ text, onShortPress, onLongPress }) {
  const { ...longPressHandler } = useLongPress(onLongPress, onShortPress)

  return <p {...longPressHandler}>{text}</p>
}

import { useState, useEffect } from 'react'

export default function useLongPress(
  onLongPress = () => {},
  onShortPress = () => {},
  delay = 1000
) {
  const [isPressing, setIsPressing] = useState(false)

  useEffect(() => {
    if (!isPressing) return

    const timer = setTimeout(() => {
      onLongPress()
      setIsPressing(false)
    }, delay)

    return () => {
      clearTimeout(timer)
    }
  }, [isPressing, delay, onLongPress])

  const startPress = () => setIsPressing(true)
  const endPress = () => {
    if (isPressing) {
      onShortPress()
      setIsPressing(false)
    }
  }

  return {
    onMouseDown: startPress,
    onTouchStart: startPress,
    onMouseUp: endPress,
    onTouchEnd: endPress,
  }
}

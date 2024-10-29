import { motion, Variants } from 'framer-motion'

const random = (min: number, max: number) => Math.random() * (max - min) + min

const rectVariants = {
  initial: () => ({
    y: 40,
    height: 30,
  }),
  animate: () => ({
    y: random(5, 0),
    height: 80,
    transition: {
      duration: random(0.5, 0.7),
      ease: 'easeInOut',
      repeat: Infinity,
      repeatType: 'mirror',
    },
  }),
}

export default function SoundWave() {
  const rects = Array.from({ length: 12 })

  return (
    <motion.svg
      width="306"
      height="115"
      viewBox="0 0 306 115"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      {rects.map((_, i) => (
        <motion.rect
          key={i}
          x={i * 24}
          width="8"
          height="0"
          rx="4"
          fill="#4F46F2"
          custom={i}
          variants={rectVariants as Variants}
          initial="initial"
          animate="animate"
        />
      ))}
    </motion.svg>
  )
}

import { motion } from 'framer-motion'

const ContainerVariants = {
  initial: {
    transition: {
      staggerChildren: 0.2,
    },
  },
  animate: {
    transition: {
      staggerChildren: 0.2,
    },
  },
}

const DotVariants = {
  initial: {
    y: '0%',
  },
  animate: {
    y: '100%',
  },
}

const DotTransition = {
  duration: 0.5,
  repeat: Infinity,
  repeatType: 'reverse' as const,
  ease: 'easeInOut' as const,
}

export default function LoadingDots() {
  return (
    <div className="flex items-center justify-center w-full">
      <motion.div
        className="flex justify-around w-16 h-6"
        variants={ContainerVariants}
        initial="initial"
        animate="animate"
      >
        <motion.span
          className="block w-3 h-3 bg-white rounded-full"
          variants={DotVariants}
          transition={DotTransition}
        />
        <motion.span
          className="block w-3 h-3 bg-white rounded-full"
          variants={DotVariants}
          transition={DotTransition}
        />
        <motion.span
          className="block w-3 h-3 bg-white rounded-full"
          variants={DotVariants}
          transition={DotTransition}
        />
      </motion.div>
    </div>
  )
}

// import { create } from 'zustand'

// const usePlayerStore = create((set) => ({
//   summaryTitle: '',
//   voiceUrls: [],
//   currentIndex: 0,
//   currentVoiceUrl: '',
//   isPlaying: false,

//   setSummaryTitle: (title) => set({ summaryTitle: title }),
//   setVoiceUrls: (urls) => set({ voiceUrls: urls }),
//   setCurrentIndex: (index) =>
//     set((state) => ({
//       currentIndex: index,
//       currentVoiceUrl: state.voiceUrls[index],
//     })),
//   setIsPlaying: (playing) => set({ isPlaying: playing }),
//   reset: () =>
//     set({
//       summaryTitle: '',
//       voiceUrls: [],
//       currentIndex: 0,
//       currentVoiceUrl: '',
//       isPlaying: false,
//     }),
// }))

// export default usePlayerStore

import { create } from 'zustand'
import { persist } from 'zustand/middleware'

const usePlayerStore = create(
  persist(
    (set) => ({
      summaryTitle: '',
      voiceUrls: [],
      currentIndex: 0,
      currentVoiceUrl: '',
      isPlaying: false,

      setSummaryTitle: (title) => set({ summaryTitle: title }),
      setVoiceUrls: (urls) => set({ voiceUrls: urls }),
      setCurrentIndex: (index) =>
        set((state) => ({
          currentIndex: index,
          currentVoiceUrl: state.voiceUrls[index],
        })),
      setIsPlaying: (playing) => set({ isPlaying: playing }),
      reset: () =>
        set({
          summaryTitle: '',
          voiceUrls: [],
          currentIndex: 0,
          currentVoiceUrl: '',
          isPlaying: false,
        }),
    }),
    {
      name: 'player-storage',
      partialize: (state) => ({
        summaryTitle: state.summaryTitle,
        voiceUrls: state.voiceUrls,
        currentIndex: state.currentIndex,
        currentVoiceUrl: state.currentVoiceUrl,
      }),
    }
  )
)

export default usePlayerStore

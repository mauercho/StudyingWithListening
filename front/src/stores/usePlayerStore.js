// import { create } from 'zustand'

// const usePlayerStore = create((set) => ({
//   voiceUrl: null,
//   summaryTitle: null,
//   setVoiceUrl: (url) => set({ voiceUrl: url }),
//   setSummaryTitle: (title) => set({ summaryTitle: title }),
// }))

// export default usePlayerStore

import { create } from 'zustand'

const usePlayerStore = create((set) => ({
  summaryTitle: '',
  voiceUrls: [],
  currentIndex: 0,
  currentVoiceUrl: '',

  setSummaryTitle: (title) => set({ summaryTitle: title }),
  setVoiceUrls: (urls) => set({ voiceUrls: urls }),
  setCurrentIndex: (index) =>
    set((state) => ({
      currentIndex: index,
      currentVoiceUrl: state.voiceUrls[index],
    })),
}))

export default usePlayerStore

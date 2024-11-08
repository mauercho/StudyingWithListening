import { create } from 'zustand'

const usePlayerStore = create((set) => ({
  voiceUrl: null,
  summaryTitle: null,
  setVoiceUrl: (url) => set({ voiceUrl: url }),
  setSummaryTitle: (title) => set({ summaryTitle: title }),
}))

export default usePlayerStore

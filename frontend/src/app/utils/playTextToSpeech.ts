import { getGoogleSpeech } from '@/app/apis/ttsSententce'

export const playTextToSpeech = async (text: string) => {
  const audioContext = new window.AudioContext()
  const arraybuff = await getGoogleSpeech(text)
  const audiobuff = await audioContext.decodeAudioData(arraybuff)
  const source = await audioContext.createBufferSource()
  source.buffer = audiobuff
  await source.connect(audioContext.destination)
  source.start()
}

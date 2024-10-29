'use server'
import * as textToSpeech from '@google-cloud/text-to-speech'
import * as speech from '@google-cloud/speech'
import fs from 'fs'
import util from 'util'

const option = {
  keyFilename: process.env.NEXT_GOOGLE_API_PATH,
}

export const getSpeech = async (inputText: string) => {
  const client = new textToSpeech.TextToSpeechClient(option)
  const request: textToSpeech.protos.google.cloud.texttospeech.v1.ISynthesizeSpeechRequest =
    {
      input: { text: inputText },
      voice: {
        languageCode: 'ko-KR',
        ssmlGender: 'MALE',
        name: 'ko-KR-Standard-D',
      },
      audioConfig: { audioEncoding: 'MP3' },
    }

  const [response] = await client.synthesizeSpeech(request)
  const audioContent = response.audioContent
  if (audioContent) {
    const base64Audio = Buffer.from(audioContent).toString('base64')
    return JSON.stringify({ blob: base64Audio })
  } else {
    throw new Error('Audio Content is undefined')
  }
}

export const postSpeech = async (audio: string) => {
  const client = new speech.SpeechClient(option)
  const request:speech.protos.google.cloud.speech.v1p1beta1.IRecognizeRequest = {
    config : {
      encoding: "WEBM_OPUS",
      languageCode: "ko-KR",
      enableAutomaticPunctuation: true,
      enableSpokenPunctuation: {value:true},
      useEnhanced:true
    },
    audio: {
      content: audio
    }
  }

  const [response] = await client.recognize(request)
  if (response.results) {
    const transcription = response.results
    .map(result => result.alternatives ? result.alternatives[0].transcript : '')
    .join('\n');
    return transcription
  } else {
    throw new Error('Text Content is undefined')
  }
}
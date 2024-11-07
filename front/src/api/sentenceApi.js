import baseApi from './baseApi'

class sentenceApi extends baseApi {
  constructor() {
    super('sentences')
  }

  patchSentenceNew(sentenceId) {
    const data = {
      isDetail: 'false',
    }
    return this.patch(`${sentenceId}`, data)
  }

  patchSentenceDetail(sentenceId) {
    const data = {
      isDetail: 'true',
    }
    return this.patch(`${sentenceId}`, data)
  }

  getSentenceVoice(sentenceId) {
    return this.get(`${sentenceId}`)
  }

  postSentenceFolding(sentenceId) {
    return this.post(`${sentenceId}`)
  }
}

export default new sentenceApi()

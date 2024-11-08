import baseApi from './baseApi'

class sentencesApi extends baseApi {
  constructor() {
    super('sentences')
  }

  patchSentenceNew(sentenceId) {
    const data = {
      isDetail: false,
    }
    return this.patch(`/${sentenceId}`, data)
  }

  patchSentenceDetail(sentenceId) {
    const data = {
      isDetail: true,
    }
    return this.patch(`/${sentenceId}`, data)
  }

  getSentenceVoice(sentenceId) {
    return this.get(`/${sentenceId}`)
  }

  postSentenceFolding(sentenceId, status) {
    const data = {
      openStatus: !status,
    }
    return this.post(`/${sentenceId}`, data)
  }

  deleteSentence(sentenceId) {
    return this.delete(`/${sentenceId}`)
  }
}

export default new sentencesApi()

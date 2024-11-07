import baseApi from './baseApi'

class summariesApi extends baseApi {
  constructor() {
    super('summaries')
  }

  getSummaries() {
    return this.get()
  }

  getSummariesDetail(summaryId) {
    return this.get(`${summaryId}`)
  }

  getSummariesIndexes(summaryId) {
    return this.get(`${summaryId}/indexes`)
  }

  postSummaries(summariesData) {
    return this.post('', summariesData)
  }

  patchSummaries(summaryId, summariesData) {
    return this.patch(`${summaryId}`, summariesData)
  }
}

export default new summariesApi()

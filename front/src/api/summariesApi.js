import baseApi from './baseApi'

class summariesApi extends baseApi {
  constructor() {
    super('summaries')
  }

  getSummaries() {
    return this.get()
  }

  getSummariesDetail(summaryId) {
    return this.get(`/${summaryId}`)
  }

  getSummariesIndexes(summaryId) {
    return this.get(`/${summaryId}/indexes`)
  }

  postSummaries(data, timeout = 60000) {
    // timeout 60000ms
    return this.post('', data, { timeout })
  }

  patchSummaries(summaryId, summariesData) {
    return this.patch(`/${summaryId}`, summariesData)
  }

  patchSummaryTitle(summaryId, title) {
    const data = {
      title,
    }
    return this.put(`/${summaryId}`, data)
  }
}

export default new summariesApi()

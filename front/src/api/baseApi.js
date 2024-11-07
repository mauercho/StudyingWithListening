import api from './api'

class baseApi {
  constructor(endpoint) {
    this.endpoint = endpoint
  }

  get(url = '', config = {}) {
    return api.get(`${this.endpoint}/${url}`, config)
  }

  post(url = '', data = {}, config = {}) {
    return api.post(`${this.endpoint}/${url}`, data, config)
  }

  put(url = '', data = {}, config = {}) {
    return api.put(`${this.endpoint}/${url}`, data, config)
  }

  patch(url = '', data = {}, config = {}) {
    return api.patch(`${this.endpoint}/${url}`, data, config)
  }

  delete(url = '', config = {}) {
    return api.delete(`${this.endpoint}/${url}`, config)
  }
}

export default baseApi

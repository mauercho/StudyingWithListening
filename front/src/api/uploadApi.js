import baseApi from './baseApi'

class uploadApi extends baseApi {
    constructor() {
        super("summaries")
    }

    postUpload(uploadData) {
        return this.post(uploadData);
    }
}

export default new uploadApi()
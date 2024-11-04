package com.ssafy.a304.shortgong.domain.uploadContent.service;

import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;

public interface UploadContentService {

	UploadContent selectUploadContentById(Long id) throws Exception;

}

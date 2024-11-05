package com.ssafy.a304.shortgong.domain.uploadContent.service;

import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.global.error.CustomException;

public interface UploadContentService {

	UploadContent selectUploadContentById(Long id) throws CustomException;

	UploadContent saveUploadContent(UploadContent uploadContent);
}

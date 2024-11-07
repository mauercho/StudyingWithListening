package com.ssafy.a304.shortgong.domain.uploadContent.service;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;
import com.ssafy.a304.shortgong.global.error.CustomException;

public interface UploadContentService {

	UploadContent selectUploadContentById(Long id) throws CustomException;

	UploadContent saveUploadContent(User user, String text, String fileName);

	UploadContent convertFileToText(UploadContent uploadContent);

	String uploadContentFile(MultipartFile contentFile);
}

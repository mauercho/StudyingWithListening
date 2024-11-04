package com.ssafy.a304.shortgong.domain.uploadContent.service;

import static com.ssafy.a304.shortgong.global.errorCode.UploadContentErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.uploadContent.repository.UploadContentRepository;
import com.ssafy.a304.shortgong.global.error.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadContentServiceImpl implements UploadContentService {

	private final UploadContentRepository uploadContentRepository;

	@Override
	public UploadContent selectUploadContentById(Long uploadContentId) throws CustomException {

		return uploadContentRepository.findById(uploadContentId)
			.orElseThrow(() -> new CustomException(UPLOAD_CONTENT_FIND_FAIL));
	}

	@Override
	@Transactional(readOnly = false)
	public UploadContent saveUploadContent(UploadContent uploadContent) {

		return uploadContentRepository.save(uploadContent);
	}

}

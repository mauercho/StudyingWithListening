package com.ssafy.a304.shortgong.domain.uploadContent.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.uploadContent.repository.UploadContentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadContentServiceImpl implements UploadContentService {

	private final UploadContentRepository uploadContentRepository;

	@Override
	public UploadContent selectUploadContentById(Long uploadContentId) throws IllegalArgumentException {

		return uploadContentRepository.findById(uploadContentId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id의 업로드 컨텐츠가 존재하지 않습니다."));
	}

}

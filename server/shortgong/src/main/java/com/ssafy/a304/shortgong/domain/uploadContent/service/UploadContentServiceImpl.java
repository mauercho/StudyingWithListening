package com.ssafy.a304.shortgong.domain.uploadContent.service;

import static com.ssafy.a304.shortgong.global.errorCode.UploadContentErrorCode.*;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.uploadContent.repository.UploadContentRepository;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;
import com.ssafy.a304.shortgong.global.error.CustomException;
import com.ssafy.a304.shortgong.global.util.ClovaOCRUtil;
import com.ssafy.a304.shortgong.global.util.FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UploadContentServiceImpl implements UploadContentService {

	private final UploadContentRepository uploadContentRepository;
	private final ClovaOCRUtil clovaOCRUtil;

	@Override
	public UploadContent selectUploadContentById(Long uploadContentId) throws CustomException {

		return uploadContentRepository.findById(uploadContentId)
			.orElseThrow(() -> new CustomException(UPLOAD_CONTENT_FIND_FAIL));
	}

	@Override
	@Transactional(readOnly = false)
	public UploadContent saveUploadContent(User user, String text, String fileName) {

		return uploadContentRepository.save(
			UploadContent.builder()
				.user(user)
				.content(text)
				.fileName(fileName)
				.build());
	}

	/**
	 * 파일 -> 텍스트 변환
	 * @return ocr 로 변환된 텍스트 파일
	 * @author 정재영
	 * */
	@Override
	@Transactional(readOnly = false)
	public UploadContent convertFileToText(UploadContent uploadContent) {

		String fileName = FileUtil.getUploadContentUrl(uploadContent.getFileName());

		List<String> sentenceList = clovaOCRUtil.requestTextByImageUrlOcr(fileName);
		for (String sentence : sentenceList) {
			log.debug(sentence);
		}

		uploadContent.updateContent(sentenceList.toString());
		return uploadContentRepository.save(uploadContent);
	}

	@Override
	public String uploadContentFile(MultipartFile contentFile) {

		return FileUtil.uploadContentFileByUuid(contentFile, UUID.randomUUID().toString());
	}

}

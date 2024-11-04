package com.ssafy.a304.shortgong.domain.summary.facade;

import org.springframework.web.multipart.MultipartFile;

public interface SummaryFacade {

	/**
	 * 요약을 위한 컨텐츠 업로드
	 * @return summaryId (요약본 pk)
	 * @author 정재영
	 * */
	long uploadContent(MultipartFile contentFile);
}

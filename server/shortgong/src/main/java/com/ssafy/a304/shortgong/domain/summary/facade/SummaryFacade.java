package com.ssafy.a304.shortgong.domain.summary.facade;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a304.shortgong.domain.summary.model.dto.response.SummaryDetailResponse;

public interface SummaryFacade {

	/**
	 * 요약을 위한 컨텐츠 업로드
	 * @return summaryId (요약본 pk)
	 * @author 정재영
	 * */
	long uploadContent(MultipartFile contentFile);

	/**
	 * 요약본 상세 페이지에 필요한 데이터 전송
	 * @return SummaryDetailResponse
	 * @author 정재영
	 * */
	SummaryDetailResponse getSummaryDetail(Long summaryId);

	void updateTitleBySummaryId(String title, Long summaryId);
}

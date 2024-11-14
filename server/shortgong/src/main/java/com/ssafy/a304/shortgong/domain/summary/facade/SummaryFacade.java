package com.ssafy.a304.shortgong.domain.summary.facade;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a304.shortgong.domain.summary.model.dto.response.SummaryDetailResponse;
import com.ssafy.a304.shortgong.domain.summary.model.dto.response.SummaryOverviewResponse;

public interface SummaryFacade {

	/**
	 * 요약을 위한 컨텐츠 업로드 (image file)
	 * @return summaryId (요약본 pk)
	 * @author 정재영
	 * */
	long uploadContentByFile(MultipartFile contentFile);

	/**
	 * 요약을 위한 컨텐츠 업로드 (url text)
	 * @return summaryId (요약본 pk)
	 * @author 정재영
	 * */
	long uploadTextFileByUrl(String url);

	/**
	 * 요약본 상세 페이지에 필요한 데이터 전송
	 * @return SummaryDetailResponse
	 * @author 정재영
	 * */
	SummaryDetailResponse getSummaryDetail(Long summaryId);

	void updateTitleBySummaryId(String title, Long summaryId);

	List<SummaryOverviewResponse> getSummaryList();
}

package com.ssafy.a304.shortgong.domain.summary.service;

import java.util.List;

import com.ssafy.a304.shortgong.domain.summary.model.dto.response.SummaryOverviewResponse;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;

public interface SummaryService {

	Summary selectSummaryById(Long id);

	Summary createNewSummary(User loginUser,
		UploadContent uploadContent);

	Summary save(Summary summary);

	List<SummaryOverviewResponse> searchSummaryListByUser(User user);

	String getTextByCrawlingUrl(String url);

	String getTextByKeyword(String keyword);
}

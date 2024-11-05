package com.ssafy.a304.shortgong.domain.summary.service;

import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;

public interface SummaryService {

	Summary selectSummaryById(Long id);

	Summary createNewSummary(User loginUser,
		UploadContent uploadContent);
}

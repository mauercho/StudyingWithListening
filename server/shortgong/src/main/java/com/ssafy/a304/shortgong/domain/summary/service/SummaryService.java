package com.ssafy.a304.shortgong.domain.summary.service;

import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;

public interface SummaryService {

	Summary selectSummaryById(Long id) throws Exception;

}

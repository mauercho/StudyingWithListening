package com.ssafy.a304.shortgong.domain.sentence.service;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceWithOutSummaryResponse;

public interface SentenceService {

	SentenceWithOutSummaryResponse getSentenceWithoutSummary(Long sentenceId) throws Exception;
}

package com.ssafy.a304.shortgong.domain.sentence.service;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;

public interface SentenceService {

	SentenceResponse getSentence(Long sentenceId) throws Exception;
}

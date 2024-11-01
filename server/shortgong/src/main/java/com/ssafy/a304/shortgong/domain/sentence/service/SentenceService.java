package com.ssafy.a304.shortgong.domain.sentence.service;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

public interface SentenceService {

	Sentence selectSentenceById(Long id) throws Exception;

}

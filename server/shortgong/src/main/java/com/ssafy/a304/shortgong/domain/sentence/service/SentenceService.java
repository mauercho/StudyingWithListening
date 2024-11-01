package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

public interface SentenceService {

	Sentence selectSentenceById(Long id) throws Exception;

	List<Sentence> selectAllSentenceBySummaryId(Long summaryId) throws Exception;
}

package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

public interface SentenceService {

	Sentence selectSentenceById(Long id) throws Exception;
	Sentence selectSentenceById(Long id);

	List<Sentence> selectAllSentenceBySummaryId(Long summaryId);

	String convertSentenceListToString(List<Sentence> sentenceList) throws Exception;
	String convertSentenceListToString(List<Sentence> sentenceList);

	SentencesCreateResponse updateSentenceWithGPTUsingBulk(Long sentenceId, String GPTResponse) throws Exception;
}

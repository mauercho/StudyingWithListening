package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

public interface SentenceService {

	void addSentenceVoice(Sentence sentence);

	Sentence selectSentenceById(Long id);

	List<Sentence> selectAllSentenceBySummaryId(Long summaryId);

	List<SentenceResponse> searchAllSentenceResponseBySummaryId(Long summaryId);

	String convertSentenceListToString(List<Sentence> sentenceList);

	SentencesCreateResponse updateSentenceWithGPTUsingBulk(Long sentenceId, String GPTResponse) throws Exception;

	void saveSentences(List<Sentence> sentenceList);
}

package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

public interface SentenceService {

	Sentence selectSentenceById(Long id) throws Exception;

	List<Sentence> selectAllSentenceBySummaryId(Long summaryId) throws Exception;

	String convertSentenceListToString(List<Sentence> sentenceList) throws Exception;

	SentencesCreateResponse updateSentence(Long sentenceId, String GPTResponse) throws Exception;

	String recreatePrompt(String sentencesString, String sentenceContent) throws Exception;
}

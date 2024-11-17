package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.global.error.CustomException;

public interface SentenceService {

	void parseQuizSentenceList(String text, Summary summary);

	/**
	 * 문장 객체 반환
	 * @return Sentence (문장 객체)
	 * @author 이주형
	 * */
	Sentence selectSentenceById(Long sentenceId) throws CustomException;

	/**
	 * 문장 객체 리스트 반환
	 * @return List<Sentence> (문장 객체 리스트)
	 * @author 이주형
	 * */
	List<Sentence> selectAllSentenceBySummaryId(Long summaryId);

	/**
	 * 문장 객체 리스트 저장
	 * @return List<Sentence> (저장된 문장 객체 리스트)
	 * @auther 이주형
	 */
	List<Sentence> saveSentences(List<Sentence> sentences);

	List<SentenceResponse> searchAllSentenceResponseBySummaryId(Long summaryId);

	String getTextByFileUrlWithOcr(String savedFilename);

	// void setAnswersAndGetVoice(Sentence sentence, String text);
}

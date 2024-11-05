package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.global.error.CustomException;

public interface SentenceService {

	/**
	 * @apiNote 문장 객체 반환
	 * @return Sentence (문장 객체)
	 * @author 이주형
	 * */
	Sentence selectSentenceById(Long sentenceId) throws CustomException;

	/**
	 * @apiNote 문장 객체 리스트 반환
	 * @return List<Sentence> (문장 객체 리스트)
	 * @author 이주형
	 * */
	List<Sentence> selectAllSentenceBySummaryId(Long summaryId);

	/**
	 * @apiNote 문장 객체 리스트 저장
	 * @return List<Sentence> (저장된 문장 객체 리스트)
	 * @auther 이주형
	 */
	List<Sentence> saveSentences(List<Sentence> sentences);

	/**
	 * @apiNote 문장 객체 리스트를 문자열로 변환
	 * @return String (문장 객체 리스트를 문자열로 변환한 결과)
	 * @author 이주형
	 * */
	String convertSentenceListToString(List<Sentence> sentenceList);

	/**
	 * @apiNote 특정 문장을 claudeResponse로 업데이트
	 * @return SentencesCreateResponse (업데이트된 문장 객체를 리스트로 감싼 Dto)
	 * @author 이주형
	 */
	List<Sentence> getModifySentences(Sentence existingSentence, String claudeResponse);

	/**
	 * @return 문장 재생성 프롬프트 반환
	 * @author 이주형
	 */
	String getRecreatePrompt(String sentencesString, String sentenceContent);

	/**
	 * @return 문장 상세 프롬프트 반환
	 * @author 이주형
	 */
	String getDetailPrompt(String sentencesString, String sentenceContent);

	void updateSentenceOpenStatus(Long sentenceId, Boolean openStatus);
}

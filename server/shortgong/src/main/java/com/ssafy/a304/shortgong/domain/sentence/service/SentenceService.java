package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.global.error.CustomException;
import com.ssafy.a304.shortgong.global.model.entity.ClaudeResponseMessage;

public interface SentenceService {

	List<ClaudeResponseMessage> getSummarizedText(String text);

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

	/**
	 * 문장 객체 리스트를 문자열로 변환
	 * @return String (문장 객체 리스트를 문자열로 변환한 결과)
	 * @author 이주형
	 * */
	String convertSentenceListToString(List<Sentence> sentenceList);

	/**
	 * 특정 문장을 claudeResponse로 업데이트
	 * @return SentencesCreateResponse (업데이트된 문장 객체를 리스트로 감싼 Dto)
	 * @author 이주형
	 */
	SentencesCreateResponse getModifySentences(Sentence existingSentence, String claudeResponse);

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

	/**
	 * 접기/펼치기 상태 업데이트
	 * @return Sentence (업데이트된 문장 객체)
	 * @auther 이주형
	 */
	void updateSentenceOpenStatus(Long sentenceId, Boolean openStatus);

	void uploadSentenceVoice(Sentence sentence);

	List<SentenceResponse> searchAllSentenceResponseBySummaryId(Long summaryId);

	String getTextByImgFileNameWithOcr(String savedFilename);

	/**
	 * 문장 삭제
	 * @author 이주형
	 */
	void deleteSentence(Long sentenceId);
}

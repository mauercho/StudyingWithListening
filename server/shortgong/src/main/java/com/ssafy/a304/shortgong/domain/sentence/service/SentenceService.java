package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.List;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.QuestionResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.global.error.CustomException;

public interface SentenceService {

	/**
	 * 문장에 해당하는 voice 생성 & 저장
	 * @return 파일명
	 * */
	void uploadSentenceVoice(Sentence sentence);

	/**
	 * @param text : 요약할 내용
	 * @return List<ClaudeResponseMessage> : Claude 가 반환한 body 값
	 */
	// List<ClaudeResponseMessage> getSummarizedTextByAI(String text);

	/**
	 * URL로부터 텍스트를 요약
	 * @return List<ClaudeResponseMessage> (요약된 텍스트 리스트)
	 */
	// List<ClaudeResponseMessage> getSummarizedTextFromUrl(String text);

	List<Sentence> parseSummarizedSentenceList(String text, Summary summary);

	List<Sentence> parseQuizSentenceList(String text, Summary summary);

	List<Sentence> parseSummarizedSentenceListByUrl(String text, Summary summary);

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

	Sentence saveSentence(Sentence sentence);

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
	// SentencesCreateResponse getModifySentences(Sentence existingSentence, String claudeResponse);

	/**
	 * 접기/펼치기 상태 업데이트
	 * @return Sentence (업데이트된 문장 객체)
	 * @auther 이주형
	 */
	// void updateSentenceOpenStatus(Long sentenceId, Boolean openStatus);

	List<SentenceResponse> searchAllSentenceResponseBySummaryId(Long summaryId);

	String getTextByFileUrlWithOcr(String savedFilename);

	/**
	 * 문장 삭제
	 * @author 이주형
	 */
	void deleteSentence(Long sentenceId);

	/**
	 * 임시 문장 파싱 결과값 반환
	 * @return S
	 * @auther 이주형
	 */

	/**
	 * 문장 파싱 결과값 반환
	 * @return List<QuestionResponse> (문장 파싱 결과값 리스트)
	 * @auther 이주형
	 */
	public List<QuestionResponse> getQuestionList(List<String> texts);

	List<QuestionResponse> getQuestions(String text);

	Sentence setAnswers(Sentence sentence, String text);
}

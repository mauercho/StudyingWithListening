package com.ssafy.a304.shortgong.domain.sentence.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.service.SentenceService;
import com.ssafy.a304.shortgong.domain.summary.service.SummaryService;
import com.ssafy.a304.shortgong.global.util.ClaudeUtil;
import com.ssafy.a304.shortgong.global.util.PromptUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceFacadeImpl implements SentenceFacade {

	private final SummaryService summaryService;
	private final SentenceService sentenceService;
	private final ClaudeUtil claudeUtil;
	private final PromptUtil promptUtil;

	// @Override
	// @Transactional
	// public SentencesCreateResponse modifySentence(Long sentenceId, SentenceModifyRequest sentenceModifyRequest) {
	//
	// 	Sentence sentence = sentenceService.selectSentenceById(sentenceId);
	// 	Summary summary = summaryService.selectSummaryById(sentence.getSummary().getId());
	// 	String sentencesString = sentenceService.convertSentenceListToString(
	// 		sentenceService.selectAllSentenceBySummaryId(summary.getId()));
	//
	// 	if (sentenceModifyRequest.getIsDetail())
	// 		return updateSentence(sentence, claudeUtil.sendMessage(makeDetailPrompt(sentence, sentencesString)));
	// 	return updateSentence(sentence, claudeUtil.sendMessage(makeRecreatePrompt(sentence, sentencesString)));
	// }

	/**
	 * 재생성 또는 상세 내용 생성을 위한 프롬프트를 보내고, 결과를 저장
	 * @return 생성된 문장 정보
	 * @author 이주형
	 */
	// @Transactional
	// protected SentencesCreateResponse updateSentence(Sentence sentence, ClaudeResponse claudeResponse) {
	//
	// 	return sentenceService.getModifySentences(sentence, claudeResponse.getContent().get(0).getText());
	// }

	/**
	 * 재생성을 위한 프롬프트 생성
	 * @return 재생성을 위한 프롬프트
	 * @author 이주형
	 */
	// private String makeRecreatePrompt(Sentence sentence, String sentencesString) {
	//
	// 	return promptUtil.getRecreatePrompt(sentencesString, sentence.getSentenceContentNormal());
	// }

	/**
	 * 상세 내용 생성을 위한 프롬프트 생성
	 * @return 상세 내용 생성을 위한 프롬프트
	 * @author 이주형
	 */
	// private String makeDetailPrompt(Sentence sentence, String sentencesString) {
	//
	// 	return promptUtil.getDetailPrompt(sentencesString, sentence.getSentenceContentNormal());
	// }

	// @Override
	// @Transactional
	// public void changeSentenceStatusToOpen(Long sentenceId) {
	//
	// 	sentenceService.updateSentenceOpenStatus(sentenceId, true);
	// }

	// @Override
	// @Transactional
	// public void changeSentenceStatusToClose(Long sentenceId) {
	//
	// 	sentenceService.updateSentenceOpenStatus(sentenceId, false);
	// }

	// @Override
	// @Transactional
	// public void deleteSentence(Long sentenceId) {
	//
	// 	sentenceService.deleteSentence(sentenceId);
	// }

}

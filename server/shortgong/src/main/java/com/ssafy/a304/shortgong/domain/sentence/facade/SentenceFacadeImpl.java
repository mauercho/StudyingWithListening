package com.ssafy.a304.shortgong.domain.sentence.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.service.SentenceService;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.summary.service.SummaryService;
import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeResponse;
import com.ssafy.a304.shortgong.global.util.ClaudeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceFacadeImpl implements SentenceFacade {

	private final SummaryService summaryService;
	private final SentenceService sentenceService;
	private final ClaudeUtil claudeUtil;

	@Override
	@Transactional
	public SentencesCreateResponse executeGptApi(Long sentenceId) throws Exception {

		String prompt = makePrompt(sentenceId);
		ClaudeResponse claudeResponse = claudeUtil.sendMessage(prompt);
		String claudeResponseText = claudeResponse.getContent().get(0).getText();
		return sentenceService.updateSentenceWithGPTUsingBulk(sentenceId, claudeResponseText); // 호출 결과 파싱 후 저장
	}

	@Override
	@Transactional
	public String makePrompt(Long sentenceId) throws Exception {

		Sentence sentence = sentenceService.selectSentenceById(sentenceId);
		Summary summary = summaryService.selectSummaryById(sentence.getSummary().getId());

		String sentencesString = sentenceService.convertSentenceListToString(
			sentenceService.selectAllSentenceBySummaryId(summary.getId()));

		return sentenceService.reSummarizePrompt(sentencesString, sentence.getSentenceContent());
	}

}

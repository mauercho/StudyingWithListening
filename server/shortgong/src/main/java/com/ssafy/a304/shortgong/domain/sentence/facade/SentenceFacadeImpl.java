package com.ssafy.a304.shortgong.domain.sentence.facade;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.service.SentenceService;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.summary.service.SummaryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceFacadeImpl implements SentenceFacade {

	private final SummaryService summaryService;
	private final SentenceService sentenceService;

	@Override
	public SentencesCreateResponse executeGptApi(Long sentenceId) throws Exception {

		String prompt = makePrompt(sentenceId);
		// TODO: GPT API 호출 -> prompt를 파라미터로 받아서 전송
		String GPTResponse = "GPT API 호출 결과. 입니다.";
		return sentenceService.updateSentenceWithGPTUsingBulk(sentenceId, GPTResponse); // 호출 결과 파싱 후 저장
	}

	@Override
	public String makePrompt(Long sentenceId) throws Exception {

		StringBuilder sb = new StringBuilder();

		Sentence sentence = sentenceService.selectSentenceById(sentenceId);
		Summary summary = summaryService.selectSummaryById(sentence.getSummary().getId());

		String sentencesString = sentenceService.convertSentenceListToString(
			sentenceService.selectAllSentenceBySummaryId(summary.getId()));

		sb.append("나는 너에게 긴 텍스트 하나를 건네 줄 거야. 그 긴 텍스트는 다음과 같아. \n")
			.append(sentencesString)
			.append("\n\n위 텍스트의 전체 맥락을 고려하여, 내가 다음으로 전해주는 문장을 다시 작성해서 제공해줘\n---\n")
			.append(sentence.getSentenceContent());

		return sb.toString();
	}

}

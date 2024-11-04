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

		StringBuilder sb = new StringBuilder();

		Sentence sentence = sentenceService.selectSentenceById(sentenceId);
		Summary summary = summaryService.selectSummaryById(sentence.getSummary().getId());

		String sentencesString = sentenceService.convertSentenceListToString(
			sentenceService.selectAllSentenceBySummaryId(summary.getId()));

		// TODO: 아래 부분을 service 단으로 이동
		sb.append("나는 너에게 긴 텍스트 하나를 건네 줄 거야. 그 긴 텍스트는 다음과 같아. \n")
			.append(sentencesString)
			.append("\n\n위 텍스트의 전체 맥락을 고려하여, 내가 다음으로 전해주는 문장을 다시 작성해서 제공해줘\n---\n")
			.append(sentence.getSentenceContent())
			.append(
				"\n---\n'전체 텍스트의 맥락을 고려했을 때, 해당 문장을 다음과 같이 수정하면 좋을 것 같습니다:' 같은 멘트나 "
					+ "'수정 이유는 다음과 같습니다:'와 같은 멘트는 필요 없어");

		return sb.toString();
	}

}

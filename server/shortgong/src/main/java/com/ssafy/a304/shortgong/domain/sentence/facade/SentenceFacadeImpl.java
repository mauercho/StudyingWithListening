package com.ssafy.a304.shortgong.domain.sentence.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.request.SentenceModifyRequest;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.service.SentenceService;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.summary.service.SummaryService;
import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeResponse;
import com.ssafy.a304.shortgong.global.util.ClaudeUtil;
import com.ssafy.a304.shortgong.global.util.ClovaVoiceUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceFacadeImpl implements SentenceFacade {

	private final SummaryService summaryService;
	private final SentenceService sentenceService;
	private final ClaudeUtil claudeUtil;
	private final ClovaVoiceUtil clovaVoiceUtil;

	@Override
	@Transactional
	public SentencesCreateResponse modifySentence(Long sentenceId, SentenceModifyRequest sentenceModifyRequest) throws
		Exception {

		Sentence sentence = sentenceService.selectSentenceById(sentenceId);
		Summary summary = summaryService.selectSummaryById(sentence.getSummary().getId());
		String sentencesString = sentenceService.convertSentenceListToString(
			sentenceService.selectAllSentenceBySummaryId(summary.getId()));

		if (sentenceModifyRequest.getIsDetail())
			return updateSentence(sentence, claudeUtil.sendMessage(makeDetailPrompt(sentence, sentencesString)));
		return updateSentence(sentence, claudeUtil.sendMessage(makeRecreatePrompt(sentence, sentencesString)));
	}

	/**
	 * @apiNote 재생성 또는 상세 내용 생성을 위한 프롬프트를 보내고, 결과를 저장
	 * @return 생성된 문장 정보
	 * @author 이주형
	 */
	@Transactional
	protected SentencesCreateResponse updateSentence(Sentence sentence, ClaudeResponse claudeResponse) throws
		Exception {

		List<Sentence> sentences = sentenceService.getModifySentences(sentence,
			claudeResponse.getContent().get(0).getText());
		// sentences.stream()
		// 	.forEach(s -> s.updateVoiceFileName(
		// 		clovaVoiceUtil.requestVoiceByTextAndVoice(s.getSentenceContent(), DSINU_MATT.getName())));
		sentenceService.saveSentences(sentences);

		return SentencesCreateResponse.of(sentences);
	}

	/**
	 * @apiNote 재생성을 위한 프롬프트 생성
	 * @return 재생성을 위한 프롬프트
	 * @author 이주형
	 */
	private String makeRecreatePrompt(Sentence sentence, String sentencesString) {

		return sentenceService.getRecreatePrompt(sentencesString, sentence.getSentenceContent());
	}

	/**
	 * @apiNote 상세 내용 생성을 위한 프롬프트 생성
	 * @return 상세 내용 생성을 위한 프롬프트
	 * @author 이주형
	 */
	private String makeDetailPrompt(Sentence sentence, String sentencesString) {

		return sentenceService.getDetailPrompt(sentencesString, sentence.getSentenceContent());
	}

}

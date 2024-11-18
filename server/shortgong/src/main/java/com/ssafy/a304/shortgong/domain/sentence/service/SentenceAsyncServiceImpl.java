package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.QuestionResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.TwoAnswerResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.SentenceTitle;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceTitleRepository;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.global.model.dto.CacheMessageContent;
import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeResponse;
import com.ssafy.a304.shortgong.global.util.ClaudeUtil;
import com.ssafy.a304.shortgong.global.util.PromptUtil;
import com.ssafy.a304.shortgong.global.util.SentenceUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SentenceAsyncServiceImpl implements SentenceAsyncService {

	private final SentenceTitleRepository sentenceTitleRepository;
	private final SentenceRepository sentenceRepository;
	private final VoiceAsyncService voiceAsyncService;
	private final SentenceUtil sentenceUtil;
	private final PromptUtil promptUtil;
	private final ClaudeUtil claudeUtil;

	@Async
	@Override
	public void getAnswerAndVoices(QuestionResponse questionResponse, String text, Summary summary,
		AtomicInteger orderCounter) {
		// 기존 sentenceTitle 엔티티 있으면 가져오고 없으면 엔티티 생성하기
		SentenceTitle sentenceTitle = sentenceTitleRepository.findByName(questionResponse.getTitle())
			.orElseGet(() -> sentenceTitleRepository.save(
				SentenceTitle.builder()
					.name(questionResponse.getTitle())
					.build()));

		// Answer (NA, SA, DA) 들만 따로 text 요청 & 저장
		questionResponse.getQuestionAnswerResponseList()
			.forEach(questionAnswerResponse -> {

				Sentence sentence = Sentence.builder()
					.summary(summary)
					.sentenceTitle(sentenceTitle)
					.point(questionAnswerResponse.getPoint())
					.question(questionAnswerResponse.getQuestion())
					.order(orderCounter.getAndIncrement())
					.build();

				Sentence existSentence = find(sentence);
				if (existSentence == null) {
					existSentence = save(sentence);
					setAnswersAndGetVoice(existSentence, text);
				}

			});
	}

	@Async
	@Override
	public void getAnswerAndVoicesByKeyword(QuestionResponse questionResponse, String text, Summary summary,
		AtomicInteger orderCounter) {
		// 기존 sentenceTitle 엔티티 있으면 가져오고 없으면 엔티티 생성하기
		SentenceTitle sentenceTitle = sentenceTitleRepository.findByName(questionResponse.getTitle())
			.orElseGet(() -> sentenceTitleRepository.save(
				SentenceTitle.builder()
					.name(questionResponse.getTitle())
					.build()));

		// Answer (NA, SA, DA) 들만 따로 text 요청 & 저장
		questionResponse.getQuestionAnswerResponseList()
			.forEach(questionAnswerResponse -> {

				Sentence sentence = Sentence.builder()
					.summary(summary)
					.sentenceTitle(sentenceTitle)
					.point(questionAnswerResponse.getPoint())
					.question(questionAnswerResponse.getQuestion())
					.order(orderCounter.getAndIncrement())
					.build();

				Sentence existSentence = find(sentence);
				if (existSentence == null) {
					existSentence = save(sentence);
					setAnswersAndGetVoiceByKeyword(existSentence, text);
				}

			});
	}

	// @Transactional(isolation = Isolation.SERIALIZABLE)
	private Sentence find(Sentence sentence) {

		return sentenceRepository.findByQuestionAndSummary(sentence.getQuestion(), sentence.getSummary())
			.orElse(null);
	}

	// @Transactional(isolation = Isolation.SERIALIZABLE)
	private Sentence save(Sentence sentence) {

		return sentenceRepository.save(sentence);
	}

	/**
	 * 문장의 NA, SA, DA 다 넣어준 문장 반환
	 * @param sentence : 요청할 문장
	 * @param originalText : 원본 텍스트
	 * @author 정재영
	 */
	private void setAnswersAndGetVoice(Sentence sentence, String originalText) {
		// 이미 받았으면 그만 돌아!
		if (sentence.getQuestionFileName() != null) {
			return;
		}

		// String answerPrompt = promptUtil.getAnswerPrompt(
		// 	sentence.getSentenceTitle().getName(),
		// 	sentence.getPoint(),
		// 	sentence.getQuestion(),
		// 	originalText);

		CacheMessageContent cacheMessageContent = promptUtil.getCacheAnswerPrompt(
			sentence.getSentenceTitle().getName(),
			sentence.getPoint(),
			sentence.getQuestion(),
			originalText);

		// claudeUtil.sendMessageAsync(answerPrompt, new ClaudeUtil.Callback() {
		claudeUtil.sendCacheMessageAsync(cacheMessageContent, new ClaudeUtil.Callback() {
			@Override
			public void onSuccess(ClaudeResponse response) {

				log.info("Success: {}", response.getContent().get(0).getText());
				List<String> answerList = sentenceUtil.splitByNewline(response.getContent().get(0).getText());
				// 프롬프트에 T,P,Q,A 넣어서 DA, SA, NA 포함한 text 가져오기 (AI)
				TwoAnswerResponse twoAnswerResponse = getAnswersByText(answerList);
				sentence.updateThreeAnswerResponse(twoAnswerResponse);
				// TTS 요청하기
				voiceAsyncService.uploadSentenceVoice(sentence);
				sentenceRepository.save(sentence);
			}

			@Override
			public void onError(Exception e) {

				System.err.println("Error: " + e.getMessage());
			}
		});
	}

	private void setAnswersAndGetVoiceByKeyword(Sentence sentence, String originalText) {
		// 이미 받았으면 그만 돌아!
		if (sentence.getQuestionFileName() != null) {
			return;
		}

		// String answerPrompt = promptUtil.getKeywordAnswerPrompt(
		// 	sentence.getSentenceTitle().getName(),
		// 	sentence.getPoint(),
		// 	sentence.getQuestion(),
		// 	originalText);

		CacheMessageContent cacheMessageContent = promptUtil.getCacheKeywordAnswerPrompt(
			sentence.getSentenceTitle().getName(),
			sentence.getPoint(),
			sentence.getQuestion(),
			originalText);

		// claudeUtil.sendMessageAsync(answerPrompt, new ClaudeUtil.Callback() {
		claudeUtil.sendCacheMessageAsync(cacheMessageContent, new ClaudeUtil.Callback() {
			@Override
			public void onSuccess(ClaudeResponse response) {

				log.info("Success: {}", response.getContent().get(0).getText());
				List<String> answerList = sentenceUtil.splitByNewline(response.getContent().get(0).getText());
				// 프롬프트에 T,P,Q,A 넣어서 DA, SA, NA 포함한 text 가져오기 (AI)
				TwoAnswerResponse twoAnswerResponse = getAnswersByText(answerList);
				sentence.updateThreeAnswerResponse(twoAnswerResponse);
				// TTS 요청하기
				voiceAsyncService.uploadSentenceVoice(sentence);
				sentenceRepository.save(sentence);
			}

			@Override
			public void onError(Exception e) {

				System.err.println("Error: " + e.getMessage());
			}
		});
	}

	private TwoAnswerResponse getAnswersByText(List<String> sentenceList) {

		TwoAnswerResponse twoAnswerResponse = new TwoAnswerResponse();

		sentenceList.forEach(
			sentenceBeforeParsed -> {
				String prefix = sentenceBeforeParsed.substring(0, 2);
				String answer = sentenceBeforeParsed.substring(4).trim();  // 2 -> 4로 변경함

				if ("NA".equals(prefix)) {
					twoAnswerResponse.setNormalAnswer(answer);
				} else if ("SA".equals(prefix)) {
					twoAnswerResponse.setSimpleAnswer(answer);
				}
			});
		return twoAnswerResponse;
	}
}

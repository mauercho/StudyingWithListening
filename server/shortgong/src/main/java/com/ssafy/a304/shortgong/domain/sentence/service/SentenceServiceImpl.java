package com.ssafy.a304.shortgong.domain.sentence.service;

import static com.ssafy.a304.shortgong.global.errorCode.SentenceErrorCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.QuestionAnswerResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.QuestionResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.global.error.CustomException;
import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeResponse;
import com.ssafy.a304.shortgong.global.util.ClaudeUtil;
import com.ssafy.a304.shortgong.global.util.ClovaOCRUtil;
import com.ssafy.a304.shortgong.global.util.PromptUtil;
import com.ssafy.a304.shortgong.global.util.SentenceUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SentenceServiceImpl implements SentenceService {

	private final SentenceRepository sentenceRepository;
	private final SentenceAsyncService sentenceAsyncService;
	private final SentenceUtil sentenceUtil;
	private final ClovaOCRUtil clovaOCRUtil;
	private final ClaudeUtil claudeUtil;
	private final PromptUtil promptUtil;

	private final AtomicInteger orderCounter = new AtomicInteger(1);

	/**
	 * Quiz (TPQA 방식) 형식의 문장 리스트를 db에 저장하는 메서드
	 * @param text summary
	 * @return List<Sentence>
	 */
	@Async
	@Override
	public void parseQuizSentenceList(String text, Summary summary) {

		String tpqPrompt = promptUtil.TPQ(text);
		ClaudeResponse response = claudeUtil.sendMessage(tpqPrompt);

		List<String> tpqTextList = sentenceUtil.splitByNewline(response.getContent().get(0).getText());

		orderCounter.set(1);
		getQuestionListByTPQTextList(tpqTextList).forEach(
			questionResponse -> sentenceAsyncService.getAnswerAndVoices(questionResponse, text, summary, orderCounter));
	}

	/**
	 * 문장 객체 반환
	 * @return Sentence (문장 객체)
	 * @author 이주형
	 * */
	@Override
	public Sentence selectSentenceById(Long sentenceId) throws CustomException {

		return sentenceRepository.findById(sentenceId)
			.orElseThrow(() -> new CustomException(SENTENCE_FIND_FAIL));
	}

	/**
	 * 문장 객체 리스트 반환
	 * @return List<Sentence> (문장 객체 리스트)
	 * @author 이주형
	 * */
	@Override
	public List<Sentence> selectAllSentenceBySummaryId(Long summaryId) {

		return sentenceRepository.findAllBySummary_IdOrderByOrder(summaryId);
	}

	@Override
	@Transactional
	public List<Sentence> saveSentences(List<Sentence> sentences) {

		return sentenceRepository.saveAll(sentences);
	}

	/**
	 * 요약본 상세 페이지에 필요한 문장 리스트 가져오기
	 * @author 정재영
	 */
	@Override
	public List<SentenceResponse> searchAllSentenceResponseBySummaryId(Long summaryId) {

		return selectAllSentenceBySummaryId(summaryId).stream()
			.map(sentence -> SentenceResponse.builder()
				.sentence(sentence)
				.build())
			.toList();
	}

	@Override
	public String getTextByFileUrlWithOcr(String savedFilename) {

		List<String> sentenceStringList = clovaOCRUtil.requestTextByImageUrlOcr(savedFilename);
		return sentenceUtil.joinStrings(sentenceStringList);
	}

	private List<QuestionResponse> getQuestionListByTPQTextList(List<String> texts) {

		int count = 0;
		List<QuestionResponse> questionResponseList = new ArrayList<>();

		QuestionResponse questionResponse = new QuestionResponse();
		QuestionAnswerResponse questionAnswerResponse = new QuestionAnswerResponse();
		for (String text : texts)
			log.info("text: {}", text);
		for (String text : texts) {
			String sentence = text.substring(2).trim();
			switch (text.charAt(0)) {
				case 'T':
					questionResponse.setTitle(sentence);
					break;
				case 'P':
					questionAnswerResponse.setPoint(sentence);
					break;
				case 'Q': // 완성 될 때
					if (count == 5) {
						return questionResponseList;
					}
					count++;
					questionAnswerResponse.setQuestion(sentence);
					questionResponse.addAnswerResponse(questionAnswerResponse);
					questionResponseList.add(questionResponse);
					questionAnswerResponse = new QuestionAnswerResponse(); // 초기화
					break;
			}
		}
		return questionResponseList;
	}

	/**
	 * @param text : 요약할 내용
	 * @return List<ClaudeResponseMessage> : Claude 가 반환한 body 값
	 */
	// private List<ClaudeResponseMessage> getSummarizedTextByAI(String text) {
	//
	// 	String prompt = promptUtil.getSummarizedPrompt(text);
	// 	return claudeUtil.sendMessage(prompt).getContent();
	// }

	/**
	 * URL 로부터 텍스트를 요약
	 * @return List<ClaudeResponseMessage> (요약된 텍스트 리스트)
	 */
	// private List<ClaudeResponseMessage> getSummarizedTextFromUrl(String text) {
	//
	// 	String prompt = promptUtil.getUrlSummarizedPrompt(text);
	// 	return claudeUtil.sendMessage(prompt).getContent();
	// }

	// @Override
	// @Transactional
	// public void deleteSentence(Long sentenceId) {
	//
	// 	sentenceRepository.delete(selectSentenceById(sentenceId));
	// }

	// @Override
	// @Transactional
	// public List<Sentence> parseSummarizedSentenceList(String text, Summary summary) {
	//
	// 	orderCounter.set(1);
	// 	return getSummarizedTextByAI(text)
	// 		.stream()
	// 		.flatMap(claudeResponseMessage -> {
	// 			String summarizedText = claudeResponseMessage.getText();
	// 			List<String> summarizedSentenceList = sentenceUtil.splitByNewline(summarizedText);
	//
	// 			return summarizedSentenceList.stream()
	// 				.map(summarizedSentence ->
	// 					Sentence.builder()
	// 						.sentenceContentNormal(summarizedSentence)
	// 						.order(orderCounter.getAndIncrement())
	// 						.summary(summary)
	// 						.openStatus(true)
	// 						.build());
	// 		})
	// 		.toList();
	// }

	// @Override
	// @Transactional
	// public List<Sentence> parseSummarizedSentenceListByUrl(String text, Summary summary) {
	//
	// 	orderCounter.set(1);
	// 	return getSummarizedTextFromUrl(text).stream()
	// 		.flatMap(claudeResponseMessage -> {
	// 			String summarizedText = claudeResponseMessage.getText();
	// 			List<String> summarizedSentenceList = sentenceUtil.splitByNewline(summarizedText);
	//
	// 			return summarizedSentenceList.stream()
	// 				.map(summarizedSentence ->
	// 					Sentence.builder()
	// 						.sentenceContentNormal(summarizedSentence)
	// 						.order(orderCounter.getAndIncrement())
	// 						.summary(summary)
	// 						.openStatus(true)
	// 						.build());
	// 		})
	// 		.toList();
	// }

	// @Override
	// @Transactional
	// public Sentence saveSentence(Sentence sentence) {
	//
	// 	return sentenceRepository.save(sentence);
	// }

	// @Override
	// public String convertSentenceListToString(List<Sentence> sentenceList) {
	//
	// 	StringBuilder sb = new StringBuilder();
	// 	for (Sentence sentence : sentenceList) {
	// 		sb.append(sentence.getSentenceContentNormal());
	// 		sb.append(" ");
	// 	}
	// 	return sb.toString();
	// }

	// @Override
	// @Transactional
	// public SentencesCreateResponse getModifySentences(Sentence existingSentence, String claudeResponse) {
	//
	// 	List<String> newSentences = sentenceUtil.splitToSentences(claudeResponse);
	// 	Long summaryId = existingSentence.getSummary().getId();
	// 	int existingOrder = existingSentence.getOrder();
	//
	// 	// 벌크 연산으로 기존 문장 다음 order들을 newSenteces의 size만큼 증가시킴
	// 	int increment = newSentences.size() - 1;
	// 	if (increment > 0)
	// 		sentenceRepository.bulkUpdateOrder(summaryId, existingOrder, increment);
	//
	// 	// 문장 업데이트
	// 	existingSentence.setSentenceContentNormal(newSentences.get(0));
	// 	existingSentence.updateVoiceFileName(
	// 		createNewSentenceVoice(newSentences.get(0), existingSentence.getSummary().getFolderName()));
	// 	List<Sentence> newSentenceEntities = new ArrayList<>(List.of(existingSentence));
	// 	for (int i = 1; i < newSentences.size(); i++) {
	// 		Sentence newSentence = Sentence.builder()
	// 			.summary(existingSentence.getSummary())
	// 			.sentenceContentNormal(newSentences.get(i))
	// 			.order(existingOrder + i)
	// 			.simpleVoiceFileName(
	// 				createNewSentenceVoice(newSentences.get(i), existingSentence.getSummary().getFolderName()))
	// 			.build();
	// 		uploadSentenceVoice(newSentence);
	// 		newSentenceEntities.add(newSentence);
	// 	}
	// 	return SentencesCreateResponse.of(saveSentences(newSentenceEntities));
	// }

	// @Override
	// @Transactional
	// public void updateSentenceOpenStatus(Long sentenceId, Boolean openStatus) {
	//
	// 	Sentence sentence = selectSentenceById(sentenceId);
	// 	sentence.updateOpenStatus(openStatus);
	// 	sentenceRepository.save(sentence);
	// }

	/**
	 * String을 받아서 문장 부호 기준으로 나누어 List로 반환
	 * @return List<String>: 문장 리스트
	 * @author 이주형
	 */
	// private List<String> splitToSentences(String text) {
	//
	// 	if (text == null || text.isEmpty()) {
	// 		return List.of("");
	// 	}
	//
	// 	List<String> sentences = new ArrayList<>();
	// 	// 정규식을 사용하여 문장과 문장 부호를 함께 캡처
	// 	Pattern pattern = Pattern.compile("[^.!?]+[.!?]");
	// 	Matcher matcher = pattern.matcher(text);
	//
	// 	while (matcher.find()) {
	// 		sentences.add(matcher.group());
	// 	}
	//
	// 	return sentences;
	// }

	/**
	 * text를 받아서 clova voice로 변환하여 voice 파일을 생성하고, 파일명을 반환
	 * @return 파일명
	 * @author 이주형
	 */
	// @Transactional
	// public String createNewSentenceVoice(String content, String folderName) {
	//
	// 	byte[] voiceData = clovaVoiceUtil.requestVoiceByTextAndVoice(content, DSINU_MATT.getName());
	// 	return S3FileUtil.uploadSentenceVoiceFileByUuid(voiceData, folderName, RandomUtil.generateUUID());
	// }

}

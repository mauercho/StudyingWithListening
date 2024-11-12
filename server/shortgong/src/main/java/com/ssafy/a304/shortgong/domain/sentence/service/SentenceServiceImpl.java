package com.ssafy.a304.shortgong.domain.sentence.service;

import static com.ssafy.a304.shortgong.global.errorCode.SentenceErrorCode.*;
import static com.ssafy.a304.shortgong.global.model.constant.ClovaVoice.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.QuestionAnswerResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.QuestionResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;
import com.ssafy.a304.shortgong.global.error.CustomException;
import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeResponse;
import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeResponseMessage;
import com.ssafy.a304.shortgong.global.util.ClaudeUtil;
import com.ssafy.a304.shortgong.global.util.ClovaOCRUtil;
import com.ssafy.a304.shortgong.global.util.ClovaVoiceUtil;
import com.ssafy.a304.shortgong.global.util.FileUtil;
import com.ssafy.a304.shortgong.global.util.PromptUtil;
import com.ssafy.a304.shortgong.global.util.RandomUtil;
import com.ssafy.a304.shortgong.global.util.SentenceUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceServiceImpl implements SentenceService {

	private final SentenceRepository sentenceRepository;
	private final ClovaVoiceUtil clovaVoiceUtil;
	private final ClaudeUtil claudeUtil;
	private final SentenceUtil sentenceUtil;
	private final ClovaOCRUtil clovaOCRUtil;
	private final PromptUtil promptUtil;

	/**
	 * 문장에 해당하는 voice 생성 & 저장
	 * @return 파일명
	 * */
	@Override
	public void uploadSentenceVoice(Sentence sentence) {

		byte[] voiceData = clovaVoiceUtil.requestVoiceByTextAndVoice(sentence.getSentenceContentNormal(),
			DSINU_MATT.getName());

		// TODO : 이미 파일이 존재하면 삭제하기

		String fileName = FileUtil.uploadSentenceVoiceFileByUuid(
			voiceData,
			sentence.getSummary().getFolderName(),
			RandomUtil.generateUUID());

		sentence.updateVoiceFileName(fileName);
	}

	/**
	 * @param text : 요약할 내용
	 * @return List<ClaudeResponseMessage> : Claude 가 반환한 body 값
	 */
	@Override
	public List<ClaudeResponseMessage> getSummarizedTextByAI(String text) {

		String prompt = promptUtil.getSummarizedPrompt(text);

		ClaudeResponse claudeResponse = claudeUtil.sendMessage(prompt);

		return claudeResponse.getContent();
	}

	/**
	 * URL로부터 텍스트를 요약
	 * @return List<ClaudeResponseMessage> (요약된 텍스트 리스트)
	 */
	@Override
	public List<ClaudeResponseMessage> getSummarizedTextFromUrl(String text) {

		String prompt = promptUtil.getUrlSummarizedPrompt(text);

		ClaudeResponse claudeResponse = claudeUtil.sendMessage(prompt);

		return claudeResponse.getContent();
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
	public Sentence saveSentence(Sentence sentence) {

		return sentenceRepository.save(sentence);
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
	public String getTextByImgFileNameWithOcr(String savedFilename) {

		List<String> sentenceStringList = clovaOCRUtil.requestTextByImageUrlOcr(savedFilename);
		return sentenceUtil.joinStrings(sentenceStringList);
	}

	@Override
	public String convertSentenceListToString(List<Sentence> sentenceList) {

		StringBuilder sb = new StringBuilder();
		for (Sentence sentence : sentenceList) {
			sb.append(sentence.getSentenceContentNormal());
			sb.append(" ");
		}
		return sb.toString();
	}

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

	/**
	 * String을 받아서 문장 부호 기준으로 나누어 List로 반환
	 * @return List<String>: 문장 리스트
	 * @author 이주형
	 */
	private List<String> splitToSentences(String text) {

		if (text == null || text.isEmpty()) {
			return List.of("");
		}

		List<String> sentences = new ArrayList<>();
		// 정규식을 사용하여 문장과 문장 부호를 함께 캡처
		Pattern pattern = Pattern.compile("[^.!?]+[.!?]");
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			sentences.add(matcher.group());
		}

		return sentences;
	}

	// @Override
	// @Transactional
	// public void updateSentenceOpenStatus(Long sentenceId, Boolean openStatus) {
	//
	// 	Sentence sentence = selectSentenceById(sentenceId);
	// 	sentence.updateOpenStatus(openStatus);
	// 	sentenceRepository.save(sentence);
	// }

	/**
	 * text를 받아서 clova voice로 변환하여 voice 파일을 생성하고, 파일명을 반환
	 * @return 파일명
	 * @author 이주형
	 */
	@Transactional
	public String createNewSentenceVoice(String content, String folderName) {

		byte[] voiceData = clovaVoiceUtil.requestVoiceByTextAndVoice(content, DSINU_MATT.getName());
		return FileUtil.uploadSentenceVoiceFileByUuid(voiceData, folderName, RandomUtil.generateUUID());
	}

	@Override
	@Transactional
	public void deleteSentence(Long sentenceId) {

		sentenceRepository.delete(selectSentenceById(sentenceId));
	}

	@Override
	public List<QuestionResponse> getQuestions() {

		List<QuestionAnswerResponse> list1 = new ArrayList<>();
		QuestionAnswerResponse questionAnswerResponse1 = new QuestionAnswerResponse(
			"스키마를 통한 지식 습득",
			"스키마란 무엇이며, 새로운 지식을 습득하는 과정에서 어떤 역할을 하나요?",
			"새로운 지식은 기존의 지식을 통해 습득됩니다. 사람은 자신이 이미 가지고 있는 지식을 기반으로 새로운 지식을 이해하고 해석하게 됩니다."
		);
		list1.add(questionAnswerResponse1);
		QuestionAnswerResponse questionAnswerResponse2 = new QuestionAnswerResponse(
			"스키마의 역할",
			"스키마는 학습 과정에서 어떤 역할을 하나요?",
			"스키마는 새로운 경험과 지식을 해석하고 구성하는 틀이 됩니다. 사실적 지식들이 개념화되어 만들어진 개념적 지식이 스키마가 되어, 이를 통해 새로운 경험을 해석하고 구조화하게 됩니다."
		);
		list1.add(questionAnswerResponse2);

		List<QuestionAnswerResponse> list2 = new ArrayList<>();
		QuestionAnswerResponse questionAnswerResponse3 = new QuestionAnswerResponse(
			"단순 반복학습의 한계",
			"단순히 반복해서 암기하는 것은 왜 효과적이지 않나요?",
			"단순 반복 암기만으로는 진정한 이해가 이루어지지 않습니다. 반복학습은 새로운 스키마를 만드는 과정과 함께 이루어져야 효과적인 학습이 될 수 있습니다."
		);
		list2.add(questionAnswerResponse3);
		QuestionAnswerResponse questionAnswerResponse4 = new QuestionAnswerResponse(
			"효율적인 학습 방법",
			"새로운 지식을 가장 효율적으로 학습하는 방법은 무엇인가요?",
			"새로운 지식을 학습할 때는 먼저 자신의 기존 지식을 탐색하여 가장 관련 있는 스키마를 찾고, 이를 통해 새로운 지식을 이해하고 체계화하는 것이 가장 효율적입니다."
		);
		list2.add(questionAnswerResponse4);

		List<QuestionResponse> questionResponses = new ArrayList<>();
		QuestionResponse questionResponse1 = QuestionResponse.builder()
			.title("공부와 스키마의 관계")
			.questionAnswerResponseList(list1)
			.build();
		questionResponses.add(questionResponse1);
		QuestionResponse questionResponse2 = QuestionResponse.builder()
			.title("학습 방법")
			.questionAnswerResponseList(list2)
			.build();
		questionResponses.add(questionResponse2);

		return questionResponses;
	}

	@Override
	public List<QuestionResponse> getQuestionList(List<String> texts) {

		List<QuestionResponse> questionResponseList = new ArrayList<>();

		List<QuestionAnswerResponse> questionAnswerResponseList = new ArrayList<>();

		String title = "";
		String point = "";
		String question = "";
		String answer = "";

		for (String text : texts) {
			switch (text.charAt(0)) {
				case 'T':
					if (!title.isEmpty()) {
						questionResponseList.add(QuestionResponse.of(title, new ArrayList<>(questionAnswerResponseList)));
						questionAnswerResponseList.clear();
					}
					title = text.substring(2).trim();
					break;
				case 'P':
					point = text.substring(2).trim();
					break;
				case 'Q':
					question = text.substring(2).trim();
					break;
				case 'A':
					answer = text.substring(2).trim();
					questionAnswerResponseList.add(QuestionAnswerResponse.of(point, question, answer));
					break;
			}
		}

		if (!title.isEmpty()) {
			questionResponseList.add(QuestionResponse.of(title, questionAnswerResponseList));
		}

		return questionResponseList;
	}

}

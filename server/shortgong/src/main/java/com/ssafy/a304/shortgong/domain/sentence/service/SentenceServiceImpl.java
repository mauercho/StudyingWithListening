package com.ssafy.a304.shortgong.domain.sentence.service;

import static com.ssafy.a304.shortgong.global.errorCode.SentenceErrorCode.*;
import static com.ssafy.a304.shortgong.global.model.constant.ClovaVoice.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}

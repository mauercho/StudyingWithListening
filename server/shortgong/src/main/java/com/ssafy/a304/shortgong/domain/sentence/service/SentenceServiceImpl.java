package com.ssafy.a304.shortgong.domain.sentence.service;

import static com.ssafy.a304.shortgong.global.model.constant.ClovaVoice.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;
import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeResponse;
import com.ssafy.a304.shortgong.global.model.entity.ClaudeResponseMessage;
import com.ssafy.a304.shortgong.global.util.ClaudeUtil;
import com.ssafy.a304.shortgong.global.util.ClovaOCRUtil;
import com.ssafy.a304.shortgong.global.util.ClovaVoiceUtil;
import com.ssafy.a304.shortgong.global.util.FileUtil;
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

	/**
	 * 문장에 해당하는 voice 생성 & 저장
	 * @return 파일명
	 * */
	@Override
	public void uploadSentenceVoice(Sentence sentence) {

		byte[] voiceData = clovaVoiceUtil.requestVoiceByTextAndVoice(sentence.getSentenceContent(),
			DSINU_MATT.getName());

		// TODO : 이미 파일이 존재하면 삭제하기

		String fileName = FileUtil.uploadSentenceVoiceFileByUuid(
			voiceData,
			sentence.getSummary().getFolderName(),
			RandomUtil.generateUUID());

		sentence.updateVoiceFileName(fileName);
	}

	@Override
	public List<ClaudeResponseMessage> getSummarizedText(String text) {

		String prompt = new StringBuilder()
			.append("당신은 자연어 처리의 전문가로서, 문맥의 일관성을 유지하면서 요약본을 만드는데 특화되어 있습니다. ").append("\n")
			.append("주어진 텍스트에서 특정 문장을 재생성하는 것이 당신의 임무입니다. ").append("\n")
			.append("아래의 주어진 문장을 요약해 주세요. ").append("\n")
			.append("그리고 요약본의 문장들을 개행문자(\"\n\")로 나누어 응답해주세요. ").append("\n")
			.append("-------------------------").append("\n").append("\n")
			.toString();

		ClaudeResponse claudeResponse = claudeUtil.sendMessage(prompt + text);

		return claudeResponse.getContent();
	}

	@Override
	public Sentence selectSentenceById(Long sentenceId) {

		return sentenceRepository.findById(sentenceId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id의 문장이 존재하지 않습니다."));
	}

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
	public String getTextByImgFileNameWithOcr(String savedFilename) {

		List<String> sentenceStringList = clovaOCRUtil.requestTextByImageUrlOcr(savedFilename);
		return sentenceUtil.joinStrings(sentenceStringList);
	}

	/* List<Sentence>를 받아서 스트링으로 변환 */
	@Override
	public String convertSentenceListToString(List<Sentence> sentenceList) {

		StringBuilder sb = new StringBuilder();
		for (Sentence sentence : sentenceList) {
			sb.append(sentence.getSentenceContent());
			sb.append(" ");
		}
		return sb.toString();
	}

	/* 벌크 연산 후 문장 업데이트 */
	@Override
	@Transactional
	public List<Sentence> getModifySentences(Sentence existingSentence, String claudeResponse) {

		List<String> newSentences = sentenceUtil.splitToSentences(claudeResponse);
		Long summaryId = existingSentence.getSummary().getId();
		int existingOrder = existingSentence.getOrder();

		// 벌크 연산으로 기존 문장 다음 order들을 newSenteces의 size만큼 증가시킴
		int increment = newSentences.size() - 1;
		if (increment > 0)
			sentenceRepository.bulkUpdateOrder(summaryId, existingOrder, increment);

		// 문장 업데이트
		existingSentence.setSentenceContent(newSentences.get(0));
		List<Sentence> newSentenceEntities = new ArrayList<>(List.of(existingSentence));
		for (int i = 1; i < newSentences.size(); i++) {
			Sentence newSentence = Sentence.builder()
				.summary(existingSentence.getSummary())
				.sentenceContent(newSentences.get(i))
				.order(existingOrder + i)
				.build();
			newSentenceEntities.add(newSentence);
		}
		return newSentenceEntities;
		// saveSentences(newSentenceEntities);

		// return SentencesCreateResponse.of(newSentenceEntities);
	}

	@Override
	public String getRecreatePrompt(String sentencesString, String sentenceContent) {

		return "나는 너에게 긴 텍스트 하나를 건네 줄 거야. 그 긴 텍스트는 다음과 같아. \n"
			+ sentencesString
			+ "\n\n위 텍스트의 전체 맥락을 고려하여, 내가 다음으로 전해주는 문장을 다시 작성해서 제공해줘\n---\n"
			+ sentenceContent
			+ "\n---\n'전체 텍스트의 맥락을 고려했을 때, 해당 문장을 다음과 같이 수정하면 좋을 것 같습니다:' 같은 멘트나 "
			+ "'수정 이유는 다음과 같습니다:'와 같은 멘트는 필요 없어. 2문장으로 제공해줘.";
	}

	@Override
	public String getDetailPrompt(String sentencesString, String sentenceContent) {

		return "나는 너에게 긴 텍스트 하나를 건네 줄 거야. 그 긴 텍스트는 다음과 같아. \n"
			+ sentencesString
			+ "\n\n위 텍스트의 전체 맥락을 고려하여, 내가 다음으로 전해주는 문장을 더 친절하고 자세하고 구체적으로 제공해줘\n---\n"
			+ sentenceContent
			+ "\n---\n'전체 텍스트의 맥락을 고려했을 때, 해당 문장을 다음과 같이 수정하면 좋을 것 같습니다:' 같은 멘트나 "
			+ "'수정 이유는 다음과 같습니다:'와 같은 멘트는 필요 없어. 2문장으로 제공해줘.";
	}

	@Override
	@Transactional
	public void updateSentenceOpenStatus(Long sentenceId, Boolean openStatus) {

		Sentence sentence = selectSentenceById(sentenceId);
		sentence.updateOpenStatus(openStatus);
		sentenceRepository.save(sentence);
	}

}

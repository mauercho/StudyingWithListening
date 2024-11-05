package com.ssafy.a304.shortgong.domain.sentence.service;

import static com.ssafy.a304.shortgong.global.model.constant.ClovaVoice.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;
import com.ssafy.a304.shortgong.global.util.ClovaVoiceUtil;
import com.ssafy.a304.shortgong.global.util.FileUtil;
import com.ssafy.a304.shortgong.global.util.RandomUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceServiceImpl implements SentenceService {

	private final SentenceRepository sentenceRepository;
	private final ClovaVoiceUtil clovaVoiceUtil;

	/**
	 * 문장에 해당하는 voice 생성 & 저장
	 * @return 파일명
	 * */
	@Override
	public void addSentenceVoice(Sentence sentence) {

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
	public Sentence selectSentenceById(Long id) {

		return sentenceRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 id의 문장이 존재하지 않습니다."));
	}

	@Override
	public List<Sentence> selectAllSentenceBySummaryId(Long summaryId) {

		return sentenceRepository.findAllBySummary_IdOrderByOrder(summaryId);
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
			.collect(Collectors.toList());
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
	public SentencesCreateResponse updateSentenceWithGPTUsingBulk(Long sentenceId, String GPTResponse) throws
		Exception {

		List<String> newSentences = splitToSentences(GPTResponse);
		Sentence existingSentence = selectSentenceById(sentenceId);
		Long summaryId = existingSentence.getSummary().getId();
		int existingOrder = existingSentence.getOrder();

		// 벌크 연산으로 기존 문장 다음 order들을 newSenteces의 size만큼 증가시킴
		int increment = newSentences.size();
		if (increment > 1)
			sentenceRepository.bulkUpdateOrder(summaryId, existingOrder, increment);

		// 기존 문장 내용 수정
		existingSentence.setSentenceContent(newSentences.get(0));

		// 문장 업데이트
		List<Sentence> newSentenceEntities = new ArrayList<>(List.of(existingSentence));
		for (int i = 1; i < newSentences.size(); i++) {
			Sentence newSentence = Sentence.builder()
				.summary(existingSentence.getSummary())
				.sentenceContent(newSentences.get(i))
				.order(existingOrder + i)
				.build();
			newSentenceEntities.add(newSentence);
		}
		sentenceRepository.saveAll(newSentenceEntities);

		return SentencesCreateResponse.of(newSentenceEntities);
	}

	@Override
	public void saveSentences(List<Sentence> sentenceList) {

		sentenceRepository.saveAll(sentenceList);
	}

	/* String을 받아서 문장기호를 기준으로 List<String>으로 변환 */
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

}

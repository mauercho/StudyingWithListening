package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceServiceImpl implements SentenceService {

	private final SentenceRepository sentenceRepository;

	@Override
	public Sentence selectSentenceById(Long id) throws Exception {

		return sentenceRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 id의 문장이 존재하지 않습니다."));
	}

	@Override
	public List<Sentence> selectAllSentenceBySummaryId(Long summaryId) throws Exception {

		return sentenceRepository.findAllBySummary_IdOrderByOrder(summaryId);
	}

	/* List<Sentence>를 받아서 스트링으로 변환 */
	@Override
	public String convertSentenceListToString(List<Sentence> sentenceList) throws Exception {

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
	public SentencesCreateResponse updateSentence(Long sentenceId, String claudeResponse) throws
		Exception {

		List<String> newSentences = splitToSentences(claudeResponse);
		Sentence existingSentence = selectSentenceById(sentenceId);
		Long summaryId = existingSentence.getSummary().getId();
		int existingOrder = existingSentence.getOrder();

		// 벌크 연산으로 기존 문장 다음 order들을 newSenteces의 size만큼 증가시킴
		int increment = newSentences.size() - 1;
		if (increment > 0)
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

	@Override
	public String getRecreatePrompt(String sentencesString, String sentenceContent) throws Exception {

		return "나는 너에게 긴 텍스트 하나를 건네 줄 거야. 그 긴 텍스트는 다음과 같아. \n"
			+ sentencesString
			+ "\n\n위 텍스트의 전체 맥락을 고려하여, 내가 다음으로 전해주는 문장을 다시 작성해서 제공해줘\n---\n"
			+ sentenceContent
			+ "\n---\n'전체 텍스트의 맥락을 고려했을 때, 해당 문장을 다음과 같이 수정하면 좋을 것 같습니다:' 같은 멘트나 "
			+ "'수정 이유는 다음과 같습니다:'와 같은 멘트는 필요 없어. 2문장으로 제공해줘.";
	}

}

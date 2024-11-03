package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.ArrayList;
import java.util.List;

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

	/* String을 받아서 문장기호를 기준으로 List<String>으로 변환 */
	public List<String> splitToSentences(String text) {

		return List.of(text.split("[.?!]"));
	}
}

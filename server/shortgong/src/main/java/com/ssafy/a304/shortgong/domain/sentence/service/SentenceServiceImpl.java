package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public List<String> splitToSentences(String text) throws Exception {

		return List.of(text.split("[.?!]"));
	}

}

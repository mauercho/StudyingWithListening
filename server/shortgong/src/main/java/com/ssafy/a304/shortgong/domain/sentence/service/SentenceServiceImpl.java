package com.ssafy.a304.shortgong.domain.sentence.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceWithOutSummaryResponse;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceServiceImpl implements SentenceService {

	private final SentenceRepository sentenceRepository;

	@Override
	public SentenceWithOutSummaryResponse getSentence(Long sentenceId) throws Exception {

		return SentenceWithOutSummaryResponse.from(
			sentenceRepository.findById(sentenceId).orElseThrow(() -> new Exception(
				"해당 문장이 존재하지 않습니다.")));
	}
}

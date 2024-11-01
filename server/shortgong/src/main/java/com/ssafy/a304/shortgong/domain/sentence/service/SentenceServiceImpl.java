package com.ssafy.a304.shortgong.domain.sentence.service;

import org.springframework.stereotype.Service;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SentenceServiceImpl implements SentenceService {

	private final SentenceRepository sentenceRepository;

	@Override
	public SentenceResponse getSentence(Long sentenceId) throws Exception {
		return SentenceResponse.from(sentenceRepository.findById(sentenceId).orElseThrow(() -> new Exception(
			"해당 문장이 존재하지 않습니다.")));
	}
}

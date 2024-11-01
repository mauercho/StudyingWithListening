package com.ssafy.a304.shortgong.domain.summary.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.summary.repository.SummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SummaryServiceImpl implements SummaryService {

	private final SummaryRepository summaryRepository;

	@Override
	public Summary selectSummaryById(Long id) {

		return summaryRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 id의 요약 정보가 존재하지 않습니다."));

	}
}

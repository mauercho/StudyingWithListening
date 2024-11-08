package com.ssafy.a304.shortgong.domain.index.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.index.model.dto.request.IndexCreateRequest;
import com.ssafy.a304.shortgong.domain.index.repository.IndexRepository;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IndexServiceImpl implements IndexService {

	private final IndexRepository indexRepository;

	@Override
	@Transactional
	public void createIndex(Summary summary, Sentence sentence, IndexCreateRequest request) {

		indexRepository.save(IndexCreateRequest.toEntity(summary, sentence, request));
	}

}

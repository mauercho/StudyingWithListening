package com.ssafy.a304.shortgong.domain.sentence.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceServiceImpl implements SentenceService {

	private final SentenceRepository sentenceRepository;

}

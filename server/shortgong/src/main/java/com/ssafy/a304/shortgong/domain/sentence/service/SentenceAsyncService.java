package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Async;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.QuestionResponse;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;

public interface SentenceAsyncService {

	@Async
	void getAnswerAndVoices(QuestionResponse questionResponse, String text, Summary summary,
		AtomicInteger orderCounter);

}

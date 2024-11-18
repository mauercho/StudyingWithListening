package com.ssafy.a304.shortgong.domain.sentence.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.QuestionResponse;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;

public interface SentenceAsyncService {

	@Async
	CompletableFuture<Void> getAnswerAndVoices(QuestionResponse questionResponse, String text, Summary summary);

	@Async
	CompletableFuture<Void> getAnswerAndVoicesByKeyword(QuestionResponse questionResponse, String text,
		Summary summary);
}

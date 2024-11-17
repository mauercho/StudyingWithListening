package com.ssafy.a304.shortgong.domain.sentence.service;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Async;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;

public interface VoiceAsyncService {

	@Async
	void uploadSentenceVoice(Sentence sentence) throws TaskRejectedException;
}

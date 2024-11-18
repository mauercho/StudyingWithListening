package com.ssafy.a304.shortgong.domain.alert.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.a304.shortgong.domain.alert.QuestionCreatedMessageResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SseEmitters {

	private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

	public SseEmitter add(SseEmitter emitter) {

		this.emitters.add(emitter);
		log.info("[new emitter added] {}", emitter);
		log.info("[emitter list size] {}", emitters.size());
		emitter.onCompletion(() -> {
			log.info("[onCompletion callback]");
			this.emitters.remove(emitter);    // 만료되면 리스트에서 삭제
		});
		emitter.onTimeout(() -> {
			log.info("[onTimeout callback]");
			emitter.complete();
		});

		return emitter;
	}

	public void sendQuestionCreatedMessage(Long summaryId, List<String> questions) {

		emitters.forEach(emitter -> sendQuestionCreatedMessageToEmitter(emitter, summaryId, questions));
	}

	public void sendAllAnswersCreatedMessageToEmitter(Long summaryId) {

		emitters.forEach(emitter -> sendAllAnswersCreatedMessageToEmitter(emitter, summaryId));
	}

	private void sendQuestionCreatedMessageToEmitter(SseEmitter emitter, Long summaryId, List<String> questions) {

		try {
			emitter.send(
				SseEmitter.event()
					.name("all questions of summary are created")
					.data(
						QuestionCreatedMessageResponse.builder()
							.summaryId(summaryId)
							.questions(questions)
							.build())
					.build());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	private void sendAllAnswersCreatedMessageToEmitter(SseEmitter emitter, Long summaryId) {

		try {
			emitter.send(
				SseEmitter.event()
					.name("all answers of summary are created")
					.data(summaryId)
					.build());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
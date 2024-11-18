package com.ssafy.a304.shortgong.domain.alert.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.ssafy.a304.shortgong.domain.alert.repository.SseEmitters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/alert")
@RequiredArgsConstructor
public class AlertController {

	private final SseEmitters emitters;

	@GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public ResponseEntity<SseEmitter> connect() {

		SseEmitter emitter = new SseEmitter(3 * 1000L);
		emitters.add(emitter);
		try {
			emitter.send(
				SseEmitter.event()
					.name("connect")
					.data("connected"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return ResponseEntity.ok(emitter);
	}
}

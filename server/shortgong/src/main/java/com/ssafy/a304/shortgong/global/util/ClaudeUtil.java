package com.ssafy.a304.shortgong.global.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ssafy.a304.shortgong.global.model.dto.request.ClaudeRequest;
import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeMessage;
import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaudeUtil {

	private final RestTemplate restTemplate;

	@Value("${claude.api.url}")
	private String API_URL;

	@Value("${claude.api.key}")
	private String apiKey;

	@Value("${claude.api.model}")
	private String model;

	@Value("${claude.api.temperature}")
	private Double temperature;

	@Value("${claude.api.max-tokens}")
	private Integer maxTokens;

	public ClaudeResponse sendMessage(String userMessage) {

		// 요청 데이터 설정
		ClaudeMessage userMessageObj = ClaudeMessage.builder()
			.role("user")
			.content(userMessage)
			.build();

		ClaudeRequest requestPayload = ClaudeRequest.builder()
			.model(model)
			.messages(List.of(userMessageObj))
			.temperature(temperature)
			.maxTokens(maxTokens)
			.build();

		// HTTP 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("x-api-key", apiKey);
		headers.set("anthropic-version", "2023-06-01");

		// HTTP 요청 생성
		HttpEntity<ClaudeRequest> requestEntity = new HttpEntity<>(requestPayload, headers);

		// API 요청 보내기
		ResponseEntity<ClaudeResponse> responseEntity = restTemplate.postForEntity(API_URL, requestEntity,
			ClaudeResponse.class);
		
		log.info("responseEntity: {}", responseEntity.getBody().getContent().get(0).getText());

		return responseEntity.getBody();
	}
}

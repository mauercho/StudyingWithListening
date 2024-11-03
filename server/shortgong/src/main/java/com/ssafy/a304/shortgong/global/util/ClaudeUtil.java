package com.ssafy.a304.shortgong.global.util;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssafy.a304.shortgong.global.entity.ClaudeMessage;
import com.ssafy.a304.shortgong.global.entity.dto.request.ClaudeRequest;
import com.ssafy.a304.shortgong.global.entity.dto.response.ClaudeResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClaudeUtil {

	private final RestTemplate restTemplate;
	private final String CLAUDE_API_URL = "...";
	private final String API_KEY = "...";

	public ClaudeResponse sendMessage(String userMessage) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-api-key", API_KEY);
		headers.set("anthropic-version", "2023-06-01");

		// 요청 본문 생성
		ClaudeRequest request = ClaudeRequest.builder()
			.model("claude-3-opus-20240229")
			.messages(Collections.singletonList(
				ClaudeMessage.builder()
					.role("user")
					.content(userMessage)
					.build()
			))
			.temperature(0.7)
			.maxTokens(1000)
			.build();

		HttpEntity<ClaudeRequest> entity = new HttpEntity<>(request, headers);

		return restTemplate.postForObject(
			CLAUDE_API_URL,
			entity,
			ClaudeResponse.class
		);
	}
}

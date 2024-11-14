package com.ssafy.a304.shortgong.global.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ssafy.a304.shortgong.global.model.dto.CacheControl;
import com.ssafy.a304.shortgong.global.model.dto.ClaudeMessage;
import com.ssafy.a304.shortgong.global.model.dto.MessageContent;
import com.ssafy.a304.shortgong.global.model.dto.MessageContentDetail;
import com.ssafy.a304.shortgong.global.model.dto.MessageContentInterface;
import com.ssafy.a304.shortgong.global.model.dto.MessagePdfContentDetail;
import com.ssafy.a304.shortgong.global.model.dto.MessageSource;
import com.ssafy.a304.shortgong.global.model.dto.request.ClaudePdfRequest;
import com.ssafy.a304.shortgong.global.model.dto.request.ClaudeRequest;
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

	public ClaudeResponse sendPdfMessages(String pdfUrl, String userMessage) throws IOException {

		URL url = new URL(pdfUrl);
		try (InputStream inputStream = url.openStream(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			String pdfBase64 = Base64.getEncoder().encodeToString(outputStream.toByteArray()).replaceAll("[\r\n]", "");

			MessageSource source = MessageSource.builder()
				.type("base64")
				.mediaType("application/pdf")
				.data(pdfBase64)
				.build();
			CacheControl cacheControl = CacheControl.builder()
				.type("ephemeral")
				.build();
			MessageContentInterface documentContent = MessagePdfContentDetail.builder()
				.type("document")
				.source(source)
				.cacheControl(cacheControl)
				.build();
			MessageContentInterface textContent = MessageContentDetail.builder()
				.type("text")
				.text(userMessage)
				.build();

			// 메시지 내용 생성
			MessageContent message = MessageContent.builder()
				.role("user")
				.content(List.of(documentContent, textContent))
				.build();

			// DTO 생성
			ClaudePdfRequest requestPayload = ClaudePdfRequest.builder()
				.model(model)
				.maxTokens(maxTokens)
				.messages(List.of(message))
				.build();

			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("x-api-key", apiKey);
			headers.set("anthropic-version", "2023-06-01");
			headers.set("anthropic-beta", "pdfs-2024-09-25,prompt-caching-2024-07-31");

			// HTTP 요청 엔티티 생성
			HttpEntity<ClaudePdfRequest> requestEntity = new HttpEntity<>(requestPayload, headers);

			// API 요청 보내기
			ResponseEntity<ClaudeResponse> responseEntity = restTemplate.postForEntity(API_URL, requestEntity,
				ClaudeResponse.class);

			// log.info("responseEntity: {}", responseEntity.getBody().getContent().get(0).getText());

			return responseEntity.getBody();
		}
	}

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

		// log.info("responseEntity: {}", responseEntity.getBody().getContent().get(0).getText());

		return responseEntity.getBody();
	}

}

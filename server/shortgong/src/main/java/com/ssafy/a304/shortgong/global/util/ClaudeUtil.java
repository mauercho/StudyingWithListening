package com.ssafy.a304.shortgong.global.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ssafy.a304.shortgong.global.model.dto.CacheControl;
import com.ssafy.a304.shortgong.global.model.dto.CacheMessageContent;
import com.ssafy.a304.shortgong.global.model.dto.ClaudeMessage;
import com.ssafy.a304.shortgong.global.model.dto.MessageContent;
import com.ssafy.a304.shortgong.global.model.dto.MessageContentDetail;
import com.ssafy.a304.shortgong.global.model.dto.MessageContentInterface;
import com.ssafy.a304.shortgong.global.model.dto.MessagePdfImageContentDetail;
import com.ssafy.a304.shortgong.global.model.dto.MessageSource;
import com.ssafy.a304.shortgong.global.model.dto.SystemContent;
import com.ssafy.a304.shortgong.global.model.dto.request.ClaudeCacheRequest;
import com.ssafy.a304.shortgong.global.model.dto.request.ClaudePdfRequest;
import com.ssafy.a304.shortgong.global.model.dto.request.ClaudeRequest;
import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaudeUtil {

	private final RestTemplate restTemplate;

	@Value("${claude.api.url}")
	private String apiUrl;

	@Value("${claude.api.model}")
	private String model;

	@Value("${claude.api.temperature}")
	private Double temperature;

	@Value("${claude.api.max-tokens}")
	private Integer maxTokens;

	@Value("${claude.api.keys.key-1}")
	private String apiKey1;
	@Value("${claude.api.keys.key-2}")
	private String apiKey2;
	@Value("${claude.api.keys.key-3}")
	private String apiKey3;
	@Value("${claude.api.keys.key-4}")
	private String apiKey4;
	@Value("${claude.api.keys.key-5}")
	private String apiKey5;
	@Value("${claude.api.keys.key-6}")
	private String apiKey6;
	@Value("${claude.api.keys.key-7}")
	private String apiKey7;
	@Value("${claude.api.keys.key-8}")
	private String apiKey8;
	@Value("${claude.api.keys.key-9}")
	private String apiKey9;
	@Value("${claude.api.keys.key-10}")
	private String apiKey10;
	@Value("${claude.api.keys.key-11}")
	private String apiKey11;
	@Value("${claude.api.keys.key-12}")
	private String apiKey12;
	@Value("${claude.api.keys.key-13}")
	private String apiKey13;
	@Value("${claude.api.keys.key-14}")
	private String apiKey14;
	@Value("${claude.api.keys.key-15}")
	private String apiKey15;
	@Value("${claude.api.keys.key-16}")
	private String apiKey16;
	@Value("${claude.api.keys.key-17}")
	private String apiKey17;
	@Value("${claude.api.keys.key-18}")
	private String apiKey18;
	@Value("${claude.api.keys.key-19}")
	private String apiKey19;
	@Value("${claude.api.keys.key-20}")
	private String apiKey20;

	private String[] apiKeys;

	private int apiKeyIndex = 0;

	private final Queue<Runnable> requestQueue = new ConcurrentLinkedQueue<>();
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	public ClaudeResponse sendImageMessages(String imageUrl, String userMessage) throws IOException {

		URL url = new URL(imageUrl);
		try (InputStream inputStream = url.openStream(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[8192];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			// 확장자를 기반으로 이미지 MIME 타입 설정
			String ext = S3FileUtil.getExtensionStringFromPreSignedUrl(imageUrl).toLowerCase();
			String imageType = String.join("/", Arrays.asList("image", ext));
			// log.info("imageType: {}", imageType);

			// 이미지 데이터를 Base64로 인코딩
			String imageBase64 = Base64.getEncoder()
				.encodeToString(outputStream.toByteArray())
				.replaceAll("[\r\n]", "");
			// log.info("imageBase64: {}", imageBase64);

			MessageSource source = MessageSource.builder()
				.type("base64")
				.mediaType(imageType)
				.data(imageBase64)
				.build();
			CacheControl cacheControl = CacheControl.builder()
				.type("ephemeral")
				.build();
			MessageContentInterface imageContent = MessagePdfImageContentDetail.builder()
				.type("image")
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
				.content(List.of(imageContent, textContent))
				.build();

			// DTO 생성
			ClaudePdfRequest requestPayload = ClaudePdfRequest.builder()
				.model(model)
				.maxTokens(maxTokens)
				.messages(List.of(message))
				.build();

			HttpHeaders headers = new HttpHeaders();
			headers.set("content-type", "application/json");
			headers.set("x-api-key", getApiKey());
			headers.set("anthropic-version", "2023-06-01");
			headers.set("anthropic-beta", "prompt-caching-2024-07-31");

			// HTTP 요청 엔티티 생성
			HttpEntity<ClaudePdfRequest> requestEntity = new HttpEntity<>(requestPayload, headers);

			// API 요청 보내기
			ResponseEntity<ClaudeResponse> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity,
				ClaudeResponse.class);

			// log.info("responseEntity: {}", responseEntity.getBody().getContent().get(0).getText());

			return responseEntity.getBody();
		}
	}

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
			MessageContentInterface documentContent = MessagePdfImageContentDetail.builder()
				.type("document")
				.source(source)
				.cacheControl(cacheControl)
				.build();
			MessageContentInterface textContent = MessageContentDetail.builder()
				.type("text")
				.text(userMessage)
				.build();

			MessageContent message = MessageContent.builder()
				.role("user")
				.content(List.of(documentContent, textContent))
				.build();

			ClaudePdfRequest requestPayload = ClaudePdfRequest.builder()
				.model(model)
				.maxTokens(maxTokens)
				.messages(List.of(message))
				.build();

			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
			headers.set("x-api-key", getApiKey());
			headers.set("anthropic-version", "2023-06-01");
			headers.set("anthropic-beta", "pdfs-2024-09-25,prompt-caching-2024-07-31");

			// HTTP 요청 엔티티 생성
			HttpEntity<ClaudePdfRequest> requestEntity = new HttpEntity<>(requestPayload, headers);

			// API 요청 보내기
			ResponseEntity<ClaudeResponse> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity,
				ClaudeResponse.class);

			log.info("responseEntity: {}", responseEntity.getBody().getContent().get(0).getText());
			// 로그로 토큰 보기

			return responseEntity.getBody();
		}
	}

	@Async
	public void sendMessageAsync(String userMessage, Callback callback) {
		// 요청을 Queue에 추가
		requestQueue.offer(() -> {
			try {
				ClaudeResponse response = sendMessage(userMessage);
				callback.onSuccess(response);
			} catch (Exception e) {
				callback.onError(e);
			}
		});
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
			.maxTokens(maxTokens)
			.build();

		// HTTP 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("x-api-key", getApiKey());
		headers.set("anthropic-version", "2023-06-01");

		// HTTP 요청 생성
		HttpEntity<ClaudeRequest> requestEntity = new HttpEntity<>(requestPayload, headers);

		// API 요청 보내기
		ResponseEntity<ClaudeResponse> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity,
			ClaudeResponse.class);

		// log.info("responseEntity: {}", responseEntity.getBody().getContent().get(0).getText());

		return responseEntity.getBody();
	}

	@Async
	public void sendCacheMessageAsync(CacheMessageContent cacheMessageContent, Callback callback) {
		// 요청을 Queue에 추가
		requestQueue.offer(() -> {
			try {
				ClaudeResponse response = sendCacheMessage(cacheMessageContent);
				callback.onSuccess(response);
			} catch (Exception e) {
				callback.onError(e);
			}
		});
	}

	/**
	 * 캐시 메시지를 보내는 메소드
	 */
	public ClaudeResponse sendCacheMessage(CacheMessageContent cacheMessageContent) {

		CacheControl cacheControl = CacheControl.builder()
			.type("ephemeral")
			.build();
		SystemContent systemContent = SystemContent.builder()
			.type("text")
			.text(cacheMessageContent.getCacheMessage())
			.cacheControl(cacheControl)
			.build();

		// 요청 데이터 설정
		ClaudeMessage userMessageObj = ClaudeMessage.builder()
			.role("user")
			.content(cacheMessageContent.getUserMessage())
			.build();

		ClaudeCacheRequest requestPayload = ClaudeCacheRequest.builder()
			.model(model)
			.messages(List.of(userMessageObj))
			.maxTokens(maxTokens)
			.system(List.of(systemContent))
			.build();

		// HTTP 헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("x-api-key", getApiKey());
		headers.set("anthropic-version", "2023-06-01");
		headers.set("anthropic-beta", "prompt-caching-2024-07-31");

		// HTTP 요청 생성
		HttpEntity<ClaudeCacheRequest> requestEntity = new HttpEntity<>(requestPayload, headers);

		// API 요청 보내기
		ResponseEntity<ClaudeResponse> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity,
			ClaudeResponse.class);

		// log.info("responseEntity: {}", responseEntity.getBody().getContent().get(0).getText());
		log.info("cache_creation_input_tokens: {}", responseEntity.getBody().getUsage().getCacheCreationInputTokens());
		log.info("cache_read_input_tokens: {}", responseEntity.getBody().getUsage().getCacheReadInputTokens());

		return responseEntity.getBody();
	}

	public interface Callback {

		void onSuccess(ClaudeResponse response);

		void onError(Exception e);
	}

	private String getApiKey() {

		apiKeyIndex = (apiKeyIndex + 1) % apiKeys.length;
		return apiKeys[apiKeyIndex];
	}

	@PostConstruct
	private void initializeApiKeys() {

		apiKeys = new String[] {
			apiKey1, apiKey2, apiKey3, apiKey4, apiKey5, apiKey6, apiKey7, apiKey8, apiKey9, apiKey10,
			apiKey11, apiKey12, apiKey13, apiKey14, apiKey15, apiKey16, apiKey17, apiKey18, apiKey19, apiKey20,
		};

		scheduler.scheduleAtFixedRate(() -> {
			for (int i = 0; i < 5 && !requestQueue.isEmpty(); i++) {
				Runnable requestTask = requestQueue.poll();
				if (requestTask != null) {
					requestTask.run();
				}
			}
		}, 0, 6, TimeUnit.SECONDS);
	}

}

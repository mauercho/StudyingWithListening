package com.ssafy.a304.shortgong.global.util;

import static com.ssafy.a304.shortgong.global.errorCode.ClovaErrorCode.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.auth.oauth2.GoogleCredentials;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmbeddingUtil {

	private final RestTemplate restTemplate;

	@Value("${google.cloud.api-url}")
	private String apiUrl;

	@Value("${google.cloud.auth-file-path}")
	private String authFilePath;

	// 구글 클라우드 access token 발급
	private String getAccessToken() throws IOException {

		GoogleCredentials credentials = GoogleCredentials
			.fromStream(new FileInputStream(authFilePath))
			.createScoped("https://www.googleapis.com/auth/cloud-platform");
		credentials.refreshIfExpired();
		return credentials.getAccessToken().getTokenValue();
	}

	// Embedding 결과 값 리턴
	public String getTextEmbedding(String content) {

		Map<String, Object> instance = new HashMap<>();
		instance.put("task_type", "SEMANTIC_SIMILARITY");
		instance.put("content", content);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("instances", new Map[] {instance});


	// 	try {
	// 		String response = restTemplate.exchange(
	// 			naverOCRConfig.getNaverOCRUrl(),
	// 			HttpMethod.POST,
	// 			request,
	// 			String.class
	// 		).getBody();
	//
	// 		return parseSentencesFromResponse(response);
	// 	} catch (RestClientException e) {
	// 		log.debug("{} : {}", NAVER_CLOVA_OCR_REQUEST_FAIL.getMessage(), e.getMessage());
	// 		// TODO: 커스텀 Exception 변경하기
	// 		throw new IllegalArgumentException(e.getMessage());
	// 	}
	// 	return webClient.post()
	// 		.uri(apiUrl)
	// 		.header("Authorization", "Bearer " + getAccessToken())
	// 		.bodyValue(requestBody)
	// 		.retrieve()
	// 		.bodyToMono(Map.class);
	// }
}

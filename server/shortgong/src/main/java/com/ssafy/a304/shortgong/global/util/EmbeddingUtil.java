package com.ssafy.a304.shortgong.global.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmbeddingUtil {

	private final RestTemplate restTemplate;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Value("${google.cloud.api-url}")
	private String apiUrl;

	@Value("${google.cloud.auth-file-path}")
	private String authFilePath;

	// Embedding 결과 값 리턴
	public double[] getTextEmbedding(String content) throws IOException {

		Map<String, Object> instance = new HashMap<>();
		instance.put("task_type", "SEMANTIC_SIMILARITY");
		instance.put("content", content);

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("instances", new Map[] {instance});

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + getAccessToken());

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
		String response = restTemplate.postForObject(apiUrl, request, String.class);

		// JSON 응답에서 values 배열만 추출
		JsonNode rootNode = objectMapper.readTree(response);
		JsonNode valuesNode = rootNode.path("predictions").get(0).path("embeddings").path("values");

		// values 배열을 double[]로 변환하여 반환
		double[] values = new double[valuesNode.size()];
		for (int i = 0; i < valuesNode.size(); i++) {
			values[i] = valuesNode.get(i).asDouble();
		}

		return values;
	}

	// 구글 클라우드 access token 발급
	private String getAccessToken() throws IOException {

		GoogleCredentials credentials = GoogleCredentials
			.fromStream(new FileInputStream(authFilePath))
			.createScoped("https://www.googleapis.com/auth/cloud-platform");
		credentials.refreshIfExpired();
		return credentials.getAccessToken().getTokenValue();
	}
}

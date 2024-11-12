package com.ssafy.a304.shortgong.global.util;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
	public String getAccessToken() throws IOException {

		GoogleCredentials credentials = GoogleCredentials
			.fromStream(new FileInputStream(authFilePath))
			.createScoped("https://www.googleapis.com/auth/cloud-platform");
		credentials.refreshIfExpired();
		return credentials.getAccessToken().getTokenValue();
	}

}

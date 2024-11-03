package com.ssafy.a304.shortgong.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class NaverTTSConfig {

	@Value("${spring.naver.tts.client-id}")
	private String clientId;

	@Value("${spring.naver.tts.client-secret}")
	private String clientSecret;

	@Value("${spring.naver.tts.url}")
	private String naverTTSUrl;
}

package com.ssafy.a304.shortgong.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class NaverOCRConfig {

	@Value("${spring.naver.ocr.url}")
	private String naverOCRUrl;

	@Value("${spring.naver.ocr.secret-key}")
	private String ocrSecret;
}

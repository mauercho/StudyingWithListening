package com.ssafy.a304.shortgong.global.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
public class CloudFrontConfig {

	@Getter
	@Value("${cloud.aws.cloudfront.url}")
	private String cloudFrontUrl;

	@Getter
	@Value("${cloud.aws.cloudfront.key-pair-id}")
	private String keyPairId;

	// @Value("${cloud.aws.cloudfront.private-key}")
	// private String base64PrivateKey;

	private String privateKeyFilePath = "private_key.pem";

	@Bean
	public PrivateKey cloudFrontPrivateKey() throws Exception {
		// .pem 파일에서 키 읽기
		String privateKeyPEM = readPEMFile(privateKeyFilePath);

		// 헤더와 푸터 제거
		privateKeyPEM = privateKeyPEM
			.replace("-----BEGIN PRIVATE KEY-----", "")
			.replace("-----END PRIVATE KEY-----", "")
			.replaceAll("\\s+", ""); // 모든 공백 제거

		// Base64 디코딩하여 PKCS8EncodedKeySpec으로 변환
		byte[] decodedKey = Base64.getDecoder().decode(privateKeyPEM);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	// PEM 파일을 문자열로 읽어오는 메서드
	private String readPEMFile(String filePath) throws IOException {

		StringBuilder contentBuilder = new StringBuilder();
		try (FileReader reader = new FileReader(new File(filePath))) {
			int ch;
			while ((ch = reader.read()) != -1) {
				contentBuilder.append((char)ch);
			}
		}
		return contentBuilder.toString();
	}
}

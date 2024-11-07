package com.ssafy.a304.shortgong.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class CloudFrontConfig {

	@Value("${cloud.aws.cloudfront.domain}")
	private String cloudFrontDomain;

	@Value("${cloud.aws.cloudfront.key-pair-id}")
	private String keyPairId;

	@Value("${cloud.aws.cloudfront.private-key}")
	private String privateKeyFilePath;

}

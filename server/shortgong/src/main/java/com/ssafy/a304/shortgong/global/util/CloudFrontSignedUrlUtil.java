package com.ssafy.a304.shortgong.global.util;

import java.security.PrivateKey;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.ssafy.a304.shortgong.global.config.CloudFrontConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CloudFrontSignedUrlUtil {

	private final CloudFrontConfig cloudFrontConfig;

	private final PrivateKey cloudFrontPrivateKey;

	public String generateSignedUrl(String relativeFilePath) {

		Date expiration = new Date(System.currentTimeMillis() + (long)60 * 1000);

		return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
			cloudFrontConfig.getCloudFrontUrl() + "/" + relativeFilePath,
			cloudFrontConfig.getKeyPairId(),
			cloudFrontPrivateKey,
			expiration
		);
	}
}

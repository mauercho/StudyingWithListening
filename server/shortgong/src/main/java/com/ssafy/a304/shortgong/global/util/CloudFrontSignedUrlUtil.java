package com.ssafy.a304.shortgong.global.util;

import java.io.File;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.ssafy.a304.shortgong.global.config.CloudFrontConfig;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CloudFrontSignedUrlUtil {

	private final CloudFrontConfig cloudFrontConfig;

	public String generateSignedUrl(String relativeFilePath) {

		Date expiration = new Date(System.currentTimeMillis() + (long)60 * 1000);
		
		String url = CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
			SignerUtils.Protocol.https,
			cloudFrontConfig.getCloudFrontUrl(),
			new File(cloudFrontConfig.getPrivateKeyFilePath()),
			relativeFilePath,
			cloudFrontConfig.getKeyPairId(),
			expiration
		);
		log.info("url: {}", url);
		return url;
	}
}

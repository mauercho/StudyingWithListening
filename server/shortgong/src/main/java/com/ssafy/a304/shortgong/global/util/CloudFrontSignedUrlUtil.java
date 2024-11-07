package com.ssafy.a304.shortgong.global.util;

import java.io.File;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils;
import com.ssafy.a304.shortgong.global.config.CloudFrontConfig;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CloudFrontSignedUrlUtil {

	private final CloudFrontConfig cloudFrontConfig;

	public String generateSignedUrl(String relativeFilePath) throws InvalidKeySpecException, IOException {

		Date expiration = new Date(System.currentTimeMillis() + (long)60 * 1000);

		return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(
			SignerUtils.Protocol.https,
			cloudFrontConfig.getCloudFrontUrl(),
			new File(cloudFrontConfig.getPrivateKeyFilePath()),
			relativeFilePath,
			cloudFrontConfig.getKeyPairId(),
			expiration
		);
	}
}

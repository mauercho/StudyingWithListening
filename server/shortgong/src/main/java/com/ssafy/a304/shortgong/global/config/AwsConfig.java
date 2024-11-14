package com.ssafy.a304.shortgong.global.config;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class AwsConfig {

	@PostConstruct
	public void init() {

		System.setProperty("aws.java.v1.printLocation", "true");
		System.setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");
	}
}

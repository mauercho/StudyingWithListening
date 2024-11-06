package com.ssafy.a304.shortgong.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {

		// 모든 경로에 "api" 접두사를 추가하되, Swagger 경로는 제외
		configurer.addPathPrefix("api", clazz -> {
			// Swagger 관련 클래스들을 제외
			String packageName = clazz.getPackageName();
			return !packageName.contains("springdoc");
		});

	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {

		registry.addMapping("/**")
			.allowedOrigins("*")  // 모든 출처 허용
			.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
			.allowedHeaders("*");
	}
}

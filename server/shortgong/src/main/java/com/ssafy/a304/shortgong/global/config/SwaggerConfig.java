package com.ssafy.a304.shortgong.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {

		return new OpenAPI()
			.addServersItem(new Server().url("https://k11a304.p.ssafy.io"))
			.info(apiInfo());
	}

	private Info apiInfo() {

		return new Info()
			.title("Shortgong API 입니다.")
			.description("Shortgong API를 사용하기 위한 문서입니다.")
			.version("1.0.0");
	}
}

package com.ssafy.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafy.global.auth.jwt.JwtAuthenticationFilter;
import com.ssafy.global.auth.jwt.JwtTokenProvider;
import com.ssafy.global.error.exception.CustomAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	private final CorsConfig corsConfig;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
			// HTTP 기본 인증을 비활성화
			.httpBasic(HttpBasicConfigurer::disable)
			// REST API에서는 보통 CSRF 보호가 필요 X
			.csrf(CsrfConfigurer::disable)
			.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
			// HTTP 요청에 대한 권한을 설정
			// JWT를 사용하여 상태를 유지하기 때문에 서버 측 세션이 필요 X
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			// HTTP 요청에 대한 권한을 설정
			.authorizeHttpRequests(authorizeRequests -> authorizeRequests
				// Swagger UI 관련 경로에 대한 모든 요청을 허용
				.requestMatchers("/api/swagger-ui.html", "/swagger-ui/**", "/api/v3/api-docs/**",
					"/swagger-resources/**", "/webjars/**", "/swagger-ui/index.html")
				.permitAll()
				// "/users/register" 경로에 대한 모든 요청을 허용
				.requestMatchers("/api/users/login")
				.permitAll()
				.requestMatchers("/api/users/refresh")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/api/users")
				.permitAll()
				// WebSocket 연결 경로 허용
				.requestMatchers("/api/lecture-chat/connections")
				.permitAll()
				// 다른 모든 요청은 인증을 필요로 함
				.anyRequest()
				.authenticated())

			.exceptionHandling(
				exceptionHandling -> exceptionHandling.authenticationEntryPoint(customAuthenticationEntryPoint))
			// JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 이전에 실행되도록 추가
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
			.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// 기본적으로는 BCrypt가 설정되며, 필요에 따라 다른 알고리즘을 추가
		// 비밀번호에 어떤 해시 알고리즘이 사용되었는지를 비밀번호와 함께 저장
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}

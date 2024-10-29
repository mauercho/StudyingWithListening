package com.ssafy.domain.user.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserSignInRequest {

	@NotBlank(message = "로그인 ID는 필수 입력 값입니다.")
	@JsonProperty("login_id")
	private String loginId;

	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	@JsonProperty("password")
	private String password;

}

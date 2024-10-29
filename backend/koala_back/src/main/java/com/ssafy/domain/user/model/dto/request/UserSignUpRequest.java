package com.ssafy.domain.user.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.user.model.entity.Auth;
import com.ssafy.domain.user.model.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSignUpRequest {

	@NotBlank(message = "유저 아이디는 필수 입력 값입니다.")
	@JsonProperty("login_id")
	private String loginId;

	@NotBlank(message = "유저 비밀번호는 필수 입력 값입니다.")
	private String password;

	@NotBlank(message = "유저 이름은 필수 입력 값입니다.")
	private String name;

	@NotBlank(message = "유저 닉네임은 필수 입력 값입니다.")
	private String nickname;

	public User toEntity(String encodedPassword, Auth auth) {
		return User.builder()
			.loginId(loginId)
			.password(encodedPassword)
			.name(name)
			.nickname(nickname)
			.auth(auth)
			.build();
	}
}

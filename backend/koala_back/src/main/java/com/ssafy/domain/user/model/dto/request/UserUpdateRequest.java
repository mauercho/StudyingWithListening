package com.ssafy.domain.user.model.dto.request;

import com.ssafy.domain.user.model.entity.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdateRequest {

	@NotBlank(message = "닉네임은 필수 입력 값입니다.")
	private String nickname;

	public User toEntity(String nickname) {
		return User.builder().nickname(nickname).build();
	}
}

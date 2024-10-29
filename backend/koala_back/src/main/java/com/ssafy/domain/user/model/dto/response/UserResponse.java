package com.ssafy.domain.user.model.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.user.model.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

	@JsonProperty("user_id")
	private Long userId;

	@JsonProperty("login_id")
	private String loginId;

	private String name;
	private String nickname;
	private Integer leaves;

	@JsonProperty("user_exp")
	private Long userExp;

	@JsonProperty("user_level")
	private Integer userLevel;

	@JsonProperty("user_created_at")
	private LocalDateTime userCreatedAt;

	@JsonProperty("auth_id")
	private Long authId;

	public static UserResponse toDto(User user) {
		return UserResponse.builder()
			.userId(user.getUserId())
			.loginId(user.getLoginId())
			.name(user.getName())
			.nickname(user.getNickname())
			.leaves(user.getLeaves())
			.userExp(user.getUserExp())
			.userLevel(user.getUserLevel())
			.userCreatedAt(user.getUserCreatedAt())
			.authId(user.getAuth().getAuthId())
			.build();
	}
}

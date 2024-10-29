package com.ssafy.domain.user.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.user.model.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserFindResponse {

	@JsonProperty("user_id")
	private Long userId;

	@JsonProperty("login_id")
	private String longinId;

	private String nickname;
	private String name;

	public static UserFindResponse toDto(User user) {
		return UserFindResponse.builder()
			.userId(user.getUserId())
			.longinId(user.getLoginId())
			.nickname(user.getNickname())
			.name(user.getName())
			.build();
	}
}

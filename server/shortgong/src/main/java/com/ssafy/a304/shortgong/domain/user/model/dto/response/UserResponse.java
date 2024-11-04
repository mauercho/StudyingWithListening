package com.ssafy.a304.shortgong.domain.user.model.dto.response;

import com.ssafy.a304.shortgong.domain.user.model.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

	private Long userId;

	private String nickname;

	private String username;

	private String profileFileName;

	public static UserResponse from(User user) {

		return UserResponse.builder()
			.userId(user.getId())
			.nickname(user.getNickname())
			.username(user.getUsername())
			.profileFileName(user.getProfileFileName())
			.build();

	}

}

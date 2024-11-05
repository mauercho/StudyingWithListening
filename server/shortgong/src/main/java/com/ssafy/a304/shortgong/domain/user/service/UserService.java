package com.ssafy.a304.shortgong.domain.user.service;

import com.ssafy.a304.shortgong.domain.user.model.dto.response.UserResponse;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;

public interface UserService {

	User selectUserById(Long userId);

	UserResponse searchUserById(Long userId) throws Exception;

	User selectLoginUser();
}

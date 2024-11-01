package com.ssafy.a304.shortgong.domain.user.service;

import com.ssafy.a304.shortgong.domain.user.model.dto.response.UserResponse;

public interface UserService {

	UserResponse searchUserById(Long userId) throws Exception;

}

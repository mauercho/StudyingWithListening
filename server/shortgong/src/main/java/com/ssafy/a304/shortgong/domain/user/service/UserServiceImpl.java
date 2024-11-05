package com.ssafy.a304.shortgong.domain.user.service;

import static com.ssafy.a304.shortgong.global.errorCode.UserErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.user.model.dto.response.UserResponse;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;
import com.ssafy.a304.shortgong.domain.user.repository.UserRepository;
import com.ssafy.a304.shortgong.global.error.CustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User selectUserById(Long userId) {

		return userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(USER_FIND_FAILED));
	}

	@Override
	public UserResponse searchUserById(Long userId) {

		return UserResponse.from(selectUserById(userId));
	}

	// TODO : 실제 로그인 유저 가져오는 로직으로 변경 필요
	@Override
	public User selectLoginUser() {

		return selectUserById(1L);
	}
}

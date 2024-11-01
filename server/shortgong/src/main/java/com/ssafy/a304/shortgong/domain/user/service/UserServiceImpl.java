package com.ssafy.a304.shortgong.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.user.model.dto.response.UserResponse;
import com.ssafy.a304.shortgong.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public UserResponse searchUserById(Long userId) throws Exception {

		return UserResponse.from(userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("해당 id의 유저 정보가 존재하지 않습니다.")));
	}
}

package com.ssafy.domain.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.domain.user.model.entity.User;
import com.ssafy.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	private UserDetails createUserDetails(User user) {
		return org.springframework.security.core.userdetails.User.builder()
			.username(user.getLoginId())
			.password(user.getPassword())
			.roles(user.getAuth().getAuthName())
			.build();
	}

	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		return userRepository.findByLoginId(loginId)
			.map(this::createUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException(loginId + "에 해당하는 회원을 찾을 수 없습니다."));
	}
}

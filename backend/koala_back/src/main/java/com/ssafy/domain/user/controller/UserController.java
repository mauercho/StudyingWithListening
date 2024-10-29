package com.ssafy.domain.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.domain.user.model.dto.request.UserSignInRequest;
import com.ssafy.domain.user.model.dto.request.UserSignUpRequest;
import com.ssafy.domain.user.model.dto.request.UserUpdateRequest;
import com.ssafy.domain.user.model.dto.response.UserFindResponse;
import com.ssafy.domain.user.model.dto.response.UserResponse;
import com.ssafy.domain.user.service.StudyTimeService;
import com.ssafy.domain.user.service.UserService;
import com.ssafy.global.auth.jwt.dto.JwtToken;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final StudyTimeService studyTimeService;

	@Operation(summary = "회원가입")
	@PostMapping
	public ResponseEntity<?> signUp(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
		UserFindResponse userFindResponse = userService.signUp(userSignUpRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(userFindResponse);
	}

	@Operation(summary = "로그인")
	@PostMapping("/login")
	public ResponseEntity<?> signIn(@Valid @RequestBody UserSignInRequest userSignInRequest) {
		String loginId = userSignInRequest.getLoginId();
		String password = userSignInRequest.getPassword();
		JwtToken jwtToken = userService.signIn(loginId, password);
		return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
	}

	@Operation(summary = "로그아웃")
	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		userService.logout();
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Logout successful!"));
	}

	@Operation(summary = "accessToken 재발급")
	@GetMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String bearerToken) {
		try {
			JwtToken jwtToken = userService.makeNewToken(bearerToken);
			if (jwtToken != null) {
				return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("message", "Invalid or expired refresh token"));
			}
		} catch (UsernameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User not found!"));
		} catch (InvalidCsrfTokenException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid refresh token!"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Map.of("message", "An unexpected error occurred"));
		}
	}

	@Operation(summary = "특정 유저 정보 조회")
	@GetMapping
	public ResponseEntity<?> getUser() {
		UserResponse userFindResponse = userService.getUser();
		return ResponseEntity.status(HttpStatus.OK).body(userFindResponse);
	}

	@Operation(summary = "닉네임 수정")
	@PatchMapping
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
		UserResponse userResponse = userService.updateUser(userUpdateRequest);
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Successfully updated user info!"));
	}

	@Operation(summary = "회원 탈퇴")
	@DeleteMapping
	public ResponseEntity<?> deleteUser() {
		userService.deleteUser();
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Successfully deleted user!"));
	}

	@Operation(summary = "랭킹 조회")
	@GetMapping("/ranking")
	public ResponseEntity<?> getRanking() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getRanking());
	}

	@Operation(summary = "유저 공부 시간 조회")
	@GetMapping("/study-time")
	public ResponseEntity<?> getStudyTime() {
		return ResponseEntity.status(HttpStatus.OK).body(studyTimeService.getStudyTime());
	}

}

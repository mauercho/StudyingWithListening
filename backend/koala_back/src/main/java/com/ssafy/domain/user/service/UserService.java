package com.ssafy.domain.user.service;

import com.ssafy.domain.user.model.dto.request.UserSignUpRequest;
import com.ssafy.domain.user.model.dto.request.UserUpdateRequest;
import com.ssafy.domain.user.model.dto.response.RankingWithMyRankResponse;
import com.ssafy.domain.user.model.dto.response.UserFindResponse;
import com.ssafy.domain.user.model.dto.response.UserResponse;
import com.ssafy.global.auth.jwt.dto.JwtToken;

public interface UserService {

	UserFindResponse signUp(UserSignUpRequest userSignUpRequest);

	JwtToken signIn(String loginId, String password);

	UserResponse getUser();

	UserResponse updateUser(UserUpdateRequest userUpdateRequest);

	void deleteUser();

	void increaseUserExp();

	void increaseUserLevel();

	JwtToken makeNewToken(String bearerToken);

	void logout();

	RankingWithMyRankResponse getRanking();

}

package com.ssafy.domain.user.model.validation;

import com.ssafy.domain.koala.model.entity.Koala;
import com.ssafy.domain.user.model.entity.Auth;
import com.ssafy.global.error.exception.UserException;

public class UserValidation {

	public static void validateLoginId(final String loginId) {
		if (loginId == null || loginId.isBlank()) {
			throw new UserException("유저의 아이디는 필수 값입니다.");
		}
	}

	public static void validatePassword(final String password) {
		if (password == null || password.isBlank()) {
			throw new UserException("유저의 비밀번호는 필수 값입니다.");
		}
	}

	public static void validateAuth(final Auth auth) {
		if (auth == null) {
			throw new UserException("유저의 권한은 필수 값입니다.");
		}
	}

	public static void validateName(final String name) {
		if (name == null || name.isBlank()) {
			throw new UserException("유저의 이름은 필수 값입니다.");
		}
	}

	public static void validateNickname(final String nickname) {
		if (nickname == null || nickname.isBlank()) {
			throw new UserException("유저의 닉네임은 필수 값입니다.");
		}
	}

	public static void validateLeaves(final Integer leaves) {
		if (leaves == null) {
			throw new UserException("유저의 잎은 필수 값입니다.");
		}
	}

	public static void validateDecreaseLeaves(final Integer leaves) {
		if (leaves == null) {
			throw new UserException("유저의 잎은 필수 값입니다.");
		}
		if (leaves <= 0) {
			throw new UserException("유저의 잎은 0보다 작을 수 없습니다.");
		}
	}

	public static void validateKoala(final Koala koala) {
		if (koala == null) {
			throw new UserException("유저의 코알라는 필수 값입니다.");
		}
	}

	public static void validateUserExp(final Long userExp) {
		if (userExp == null) {
			throw new UserException("유저의 경험치는 필수 값입니다.");
		}
	}

	public static void validateUserLevel(final Integer userLevel) {
		if (userLevel == null) {
			throw new UserException("유저의 레벨은 필수 값입니다.");
		}
	}

	public static void validate(final String loginId, final String password, final Auth auth, final String name,
		final String nickname, final Integer leaves, final Koala koala, final Long userExp, final Integer userLevel) {
		validateLoginId(loginId);
		validatePassword(password);
		validateAuth(auth);
		validateName(name);
		validateNickname(nickname);
		validateLeaves(leaves);
		validateKoala(koala);
		validateUserExp(userExp);
		validateUserLevel(userLevel);
	}
}

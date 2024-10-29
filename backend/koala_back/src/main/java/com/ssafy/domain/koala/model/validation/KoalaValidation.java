package com.ssafy.domain.koala.model.validation;

import com.ssafy.global.error.exception.KoalaException;

public class KoalaValidation {

	public static void validateKoalaName(final String koalaName) {
		if (koalaName == null || koalaName.isBlank()) {
			throw new KoalaException("코알라의 이름은 필수 값입니다.");
		}
	}

	public static void validateKoalaLevel(final Integer koalaLevel) {
		if (koalaLevel == null) {
			throw new KoalaException("코알라의 레벨은 필수 값입니다.");
		}
	}

	public static void validateKoalaExp(final Integer koalaExp) {
		if (koalaExp == null) {
			throw new KoalaException("코알라의 경험치는 필수 값입니다.");
		}
	}

	public static void validateKoalaType(final Integer koalaType) {
		if (koalaType == null) {
			throw new KoalaException("코알라의 타입은 필수 값입니다.");
		}
	}

	public static void validate(String koalaName, Integer koalaLevel, Integer koalaExp, Integer koalaType) {
		validateKoalaName(koalaName);
		validateKoalaLevel(koalaLevel);
		validateKoalaExp(koalaExp);
		validateKoalaType(koalaType);
	}
}

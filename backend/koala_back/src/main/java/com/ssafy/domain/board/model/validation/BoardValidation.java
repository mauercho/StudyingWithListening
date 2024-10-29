package com.ssafy.domain.board.model.validation;

public class BoardValidation {

	private static final String KOREAN_NUMERIC_REGEX = "^[가-힣ㄱ-ㅎㅏ-ㅣ0-9\\s.,!?]+$";

	public static boolean validateKoreanAndNumeric(String input) {
		return !input.matches(KOREAN_NUMERIC_REGEX);
	}

}

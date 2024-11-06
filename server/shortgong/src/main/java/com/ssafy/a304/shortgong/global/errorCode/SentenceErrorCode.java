package com.ssafy.a304.shortgong.global.errorCode;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SentenceErrorCode implements ErrorCode {
	SENTENCE_FIND_FAIL(NOT_FOUND, "해당 문장이 존재하지 않습니다.");

	private final HttpStatus httpStatus;

	private final String message;
}

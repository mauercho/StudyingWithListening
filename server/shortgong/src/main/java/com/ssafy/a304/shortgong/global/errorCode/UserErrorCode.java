package com.ssafy.a304.shortgong.global.errorCode;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
	USER_FIND_FAILED(BAD_REQUEST, "해당 유저 정보가 존재하지 않습니다");

	private final HttpStatus httpStatus;

	private final String message;

}

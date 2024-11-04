package com.ssafy.a304.shortgong.global.errorCode;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClovaErrorCode implements ErrorCode {
	NAVER_CLOVA_TTS_REQUEST_FAIL(BAD_REQUEST, "NAVER CLOVA TTS 요청 실패");

	private final HttpStatus httpStatus;

	private final String message;

}

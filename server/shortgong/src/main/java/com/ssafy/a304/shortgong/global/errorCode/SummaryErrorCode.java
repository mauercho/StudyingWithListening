package com.ssafy.a304.shortgong.global.errorCode;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SummaryErrorCode implements ErrorCode {
	SUMMARY_FIND_FAIL(NOT_FOUND, "해당 요약집이 존재하지 않습니다.");

	private final HttpStatus httpStatus;

	private final String message;
}

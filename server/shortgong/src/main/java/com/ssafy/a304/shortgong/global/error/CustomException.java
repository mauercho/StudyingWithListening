package com.ssafy.a304.shortgong.global.error;

import com.ssafy.a304.shortgong.global.errorCode.ErrorCode;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private final ErrorCode errorCode;

	public CustomException(ErrorCode errorCode) {

		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
}
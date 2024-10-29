package com.ssafy.global.error.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends RuntimeException {
	private final HttpStatus httpStatus;

	public TokenException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
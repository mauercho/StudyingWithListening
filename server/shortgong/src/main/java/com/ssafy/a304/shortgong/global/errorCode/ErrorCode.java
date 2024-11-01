package com.ssafy.a304.shortgong.global.errorCode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	HttpStatus getHttpStatus();

	String getMessage();
}

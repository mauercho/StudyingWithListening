package com.ssafy.a304.shortgong.global.errorCode;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UploadContentErrorCode implements ErrorCode {
	UPLOAD_CONTENT_FIND_FAIL(NOT_FOUND, "해당 업로드 컨텐츠가 존재하지 않습니다");

	private final HttpStatus httpStatus;

	private final String message;

}
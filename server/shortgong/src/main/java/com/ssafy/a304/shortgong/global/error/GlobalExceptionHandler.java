package com.ssafy.a304.shortgong.global.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.a304.shortgong.global.errorCode.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/* 입력 에러 */
	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	public ResponseEntity<ErrorResponse> handleBindException(
		BindException e) {

		Map<String, String> errorMap = new HashMap<>();

		if (e.hasErrors()) {
			BindingResult bindingResult = e.getBindingResult();

			bindingResult.getFieldErrors().forEach(
				fieldError ->
					errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
		}
		String errorMapString = errorMap.toString();

		log.error("입력 에러 : {}", errorMapString);

		return ResponseEntity
			.badRequest()
			.body(new ErrorResponse(
				HttpStatus.BAD_REQUEST, errorMapString));
	}

	/* 사용자 정의 에러 (컨트롤러에서 사용할 메서드) */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> handleException(
		CustomException e) {

		ErrorCode errorCode = e.getErrorCode();
		HttpStatus httpStatus = errorCode.getHttpStatus();

		ErrorResponse errorResponse = new ErrorResponse(
			httpStatus, errorCode.getMessage());

		log.error("사용자 정의 에러 : {}", e.getMessage());
		return ResponseEntity
			.status(httpStatus)
			.body(errorResponse);
	}

	/* 모든 예외 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(
		Exception e) {

		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorResponse errorResponse = new ErrorResponse(
			httpStatus, e.getMessage());

		log.error("서버 에러 : {}", e.getMessage(), e);
		return ResponseEntity
			.status(httpStatus)
			.body(errorResponse);
	}
}



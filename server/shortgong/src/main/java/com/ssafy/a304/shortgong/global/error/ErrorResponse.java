package com.ssafy.a304.shortgong.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {

	private final HttpStatus httpStatus;

	private final int code;

	private final String message;

	/* handler 에서 사용할 생성자 */
	public ErrorResponse(HttpStatus httpStatus, String message) {

		this.httpStatus = httpStatus;
		this.code = httpStatus.value();
		this.message = message;
	}

	/* 컨트롤러에서 사용할 메서드 */
	public static ResponseEntity<ErrorResponse> error(
		CustomException e) {

		return ResponseEntity
			.status(e.getErrorCode().getHttpStatus())
			.body(ErrorResponse.builder()
				.httpStatus(e.getErrorCode().getHttpStatus())
				.code(e.getErrorCode().getHttpStatus().value())
				.message(e.getErrorCode().getMessage())
				.build());
	}

}

package com.ssafy.a304.shortgong.global.errorCode;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileErrorCode implements ErrorCode {
	PROFILE_IMG_EXTENSION_FIND_FAILED(UNSUPPORTED_MEDIA_TYPE, "프로필 이미지 파일이 아닙니다"),
	OCR_IMG_EXTENSION_FIND_FAILED(UNSUPPORTED_MEDIA_TYPE, "OCR 가능한 이미지 파일이 아닙니다"),
	MP3_EXTENSION_FIND_FAILED(UNSUPPORTED_MEDIA_TYPE, "Mp3 파일이 아닙니다"),

	FILE_FIND_FAILED(NOT_FOUND, "해당 파일을 찾을 수 없습니다"),

	FILE_CONVERT_FAILED(BAD_REQUEST, "해당 파일을 변환할 수 없습니다"),
	FILE_SAVE_FAILED(BAD_REQUEST, "해당 파일을 저장할 수 없습니다"),
	EXTENSION_FIND_FAILED(BAD_REQUEST, "파일의 확장자가 없습니다"),
	LOCAL_FILE_DELETION_FAILED(BAD_REQUEST, "파일을 삭제할 수 없습니다"),

	CANNOT_CONVERT_MULTIPART_TO_FILE(INTERNAL_SERVER_ERROR, "MultipartFile 에서 File 로 전환에 실패했습니다"),
	DIRECTORY_CREATE_FAILED(INTERNAL_SERVER_ERROR, "디렉토리 생성에 실패했습니다"),
	FILE_CREATE_FAILED(INTERNAL_SERVER_ERROR, "새로운 파일을 생성할 수 없습니다"),
	FILE_NAME_EMPTY(INTERNAL_SERVER_ERROR, "빈 파일 이름입니다"),
	S3_FILE_DELETION_FAILED(INTERNAL_SERVER_ERROR, "S3에서 파일 삭제 중 오류 발생");

	private final HttpStatus httpStatus;

	private final String message;

}

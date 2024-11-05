package com.ssafy.a304.shortgong.global.validator;

import static com.ssafy.a304.shortgong.global.errorCode.FileErrorCode.*;

import com.ssafy.a304.shortgong.global.error.CustomException;

public class FileValidator {

	public static void checkProfileImageExt(String ext) throws CustomException {

		if (!ext.equals("png") && !ext.equals("jpg") && !ext.equals("jpeg")) {
			throw new CustomException(PROFILE_IMG_EXTENSION_FIND_FAILED);
		}
	}

	public static void checkOcrImageExt(String ext) throws CustomException {

		if (!ext.equals("png") && !ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("pdf")) {
			throw new CustomException(OCR_IMG_EXTENSION_FIND_FAILED);
		}
	}

	public static void checkMp3Ext(String ext) throws CustomException {

		if (!ext.equals("mp3")) {
			throw new CustomException(MP3_EXTENSION_FIND_FAILED);
		}
	}

	public static void checkFileNonEmpty(String fileName) throws CustomException {

		if (fileName == null || fileName.isEmpty()) {
			throw new CustomException(FILE_FIND_FAILED);
		}
	}
}

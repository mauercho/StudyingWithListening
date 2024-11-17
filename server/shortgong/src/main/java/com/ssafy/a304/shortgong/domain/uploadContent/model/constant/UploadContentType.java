package com.ssafy.a304.shortgong.domain.uploadContent.model.constant;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UploadContentType {
	IMAGE("image"),
	URL("url"),
	KEYWORD("keyword");

	public static UploadContentType fromType(String type) {

		return Arrays.stream(values())
			.filter(contentType -> contentType.type.equalsIgnoreCase(type))
			.findFirst()
			// TODO : Custom Exception
			.orElseThrow(() -> new IllegalArgumentException("Invalid UploadContentType: " + type));
	}

	private final String type;
}

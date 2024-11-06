package com.ssafy.a304.shortgong.domain.uploadContent.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UploadContentType {
	IMAGE("image"),
	URL("url");

	private final String type;
}

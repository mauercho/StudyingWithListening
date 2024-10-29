package com.ssafy.domain.koala.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.koala.model.entity.Koala;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class KoalaNameRequest {

	@NotBlank(message = "코알라 이름은 필수 입력 값입니다.")
	@JsonProperty("koala_name")
	private String koalaName;

	public Koala toEntity(Koala koala, String koalaName) {
		return Koala.builder()
			.user(koala.getUser())
			.koalaName(koalaName)
			.koalaLevel(koala.getKoalaLevel())
			.koalaExp(koala.getKoalaExp())
			.koalaType(koala.getKoalaType())
			.build();
	}

}

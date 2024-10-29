package com.ssafy.domain.koala.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.domain.koala.model.entity.Koala;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KoalaResponse {

	@JsonProperty("koala_id")
	private Long koalaId;

	@JsonProperty("koala_name")
	private String koalaName;

	@JsonProperty("koala_level")
	private Integer koalaLevel;

	@JsonProperty("koala_exp")
	private Integer koalaExp;

	@JsonProperty("koala_type")
	private Integer koalaType;

	public static KoalaResponse toDto(Koala koala) {
		return KoalaResponse.builder()
			.koalaId(koala.getKoalaId())
			.koalaName(koala.getKoalaName())
			.koalaLevel(koala.getKoalaLevel())
			.koalaExp(koala.getKoalaExp())
			.koalaType(koala.getKoalaType())
			.build();
	}
}

package com.ssafy.a304.shortgong.domain.sentence.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SentenceModifyRequest {

	@NotNull(message = "문장의 구체화 여부를 포함해주세요.")
	private Boolean isDetail;

}

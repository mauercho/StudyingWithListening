package com.ssafy.a304.shortgong.domain.sentence.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SentenceModifyRequest {

	@NotBlank(message = "문장의 구체화 여부를 포함해주세요.")
	private Boolean isDetail;

}

package com.ssafy.a304.shortgong.domain.sentence.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TwoAnswerResponse {

	private String simpleAnswer;
	private String normalAnswer;
}

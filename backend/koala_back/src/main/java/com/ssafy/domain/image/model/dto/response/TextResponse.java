package com.ssafy.domain.image.model.dto.response;

import static lombok.AccessLevel.*;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class TextResponse {

	private List<Text> texts;

	@Getter
	@Builder
	@NoArgsConstructor(access = PROTECTED)
	@AllArgsConstructor(access = PROTECTED)
	public static class Text {
		private String sentenceText;

		public static Text toDto(String text) {
			return Text.builder()
				.sentenceText(text)
				.build();
		}
	}

	public static TextResponse toDto(List<String> texts) {
		List<Text> textList = texts.stream()
			.map(Text::toDto)
			.collect(Collectors.toList());

		return TextResponse.builder()
			.texts(textList)
			.build();
	}
}

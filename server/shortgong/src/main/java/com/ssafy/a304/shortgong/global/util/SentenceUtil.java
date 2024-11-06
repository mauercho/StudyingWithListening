package com.ssafy.a304.shortgong.global.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class SentenceUtil {

	/* String을 받아서 문장기호를 기준으로 List<String>으로 변환 */
	public List<String> splitToSentences(String text) {

		if (text == null || text.isEmpty()) {
			return List.of("");
		}

		List<String> sentences = new ArrayList<>();
		// 정규식을 사용하여 문장과 문장 부호를 함께 캡처
		Pattern pattern = Pattern.compile("[^.!?]+[.!?]");
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			sentences.add(matcher.group());
		}

		return sentences;
	}

	/* 개행 문자를 기준으로 문자열을 List<String>으로 변환 */
	public List<String> splitByNewline(String text) {

		if (text == null || text.isEmpty()) {
			return List.of("");
		}

		// 개행 문자(\n 또는 \r\n) 기준으로 분리
		String[] lines = text.split("\\r?\\n");
		List<String> lineList = new ArrayList<>();

		for (String line : lines) {
			if (!line.trim().isEmpty()) { // 빈 줄 제외
				lineList.add(line.trim());
			}
		}

		return lineList;
	}

	/* List<String>을 하나의 String으로 합쳐주는 메서드 */
	public String joinStrings(List<String> stringList) {

		if (stringList == null || stringList.isEmpty()) {
			return "";
		}
		return String.join("\n", stringList); // 각 요소 사이에 개행 문자를 추가
	}
}

package com.ssafy.a304.shortgong.domain.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.a304.shortgong.global.model.dto.response.ClaudeResponse;
import com.ssafy.a304.shortgong.global.util.ClaudeUtil;
import com.ssafy.a304.shortgong.global.util.PromptUtil;
import com.ssafy.a304.shortgong.global.util.SentenceUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaudeTestService {

	private final ClaudeUtil claudeUtil;
	private final PromptUtil promptUtil;
	private final SentenceUtil sentenceUtil;

	public void testPdf() throws Exception {

		String pdfUrl = "https://shortgong.s3.us-east-1.amazonaws.com/test-upload-content/%ED%95%80%ED%85%8C%ED%81%AC_%ED%8A%B9%EA%B0%95.pdf?response-content-disposition=inline&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEFwaCXVzLWVhc3QtMSJHMEUCIFupT33I9%2BWHKe8bA%2FeAQCdG9s8tMcVpnC19bmbWT50SAiEAwDinJR3ydOSZmSmbmWwKAUwovyh%2FJO%2Bjg4jrXIbicQkq0AMI5f%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARAAGgwyMDI1MzM1MDYxMTQiDCaRl%2FuCz3dowFqqLyqkA96dtj0tORNaMqbNHnyvzewHHNyQCNKDM9VFEMxZU0J56SEYlGsodfPF6bw78gVEiDEazNX6C%2BeJ8LJrVJb1Z42svv4gDAZutZo3wxkNiVeAIwUmJJSo5iJAUnWRoXZgnAcM5WXWBUkSxqE6pLT8rNbIzM9mUNnzgXhUki3hzBA%2FwO3%2BXuo7mWlnc3QepMYm9fM%2Bszu3MFWEeq3xR3U4C%2BdJ1q%2FFEjtjYSdJudeCMF6NIK0BNWAa8jwj0y6rbQiaROB5girr1eFLXYOmOxVqsu6dFsQIuJRADk%2BhPguQczTN7iqMEG0yU339rTEysR4m8uP6JavpvL2%2BYElZrVDwEq7nk7YpWO9XPZX65jDTFpkY8vMhG5EsgAutgFmas4BxC1GRFTJO6toP3VLjkUkSuq7J0us%2Fl6UAOLzGB4%2BOj57LF%2FS%2BiIii%2BtpN9XEYDueRBgbdZY5y%2B%2FFSpwkuNtl%2FY4RwNV7auebRx%2BD9tYhU86wuC9KYGlfw%2BYHOG7b3KDW8DoBFPXjWLwlhpx0KIwE7kqmL4aP3DsBUEQeJN%2FMuJbDZ60v90jCu5NW5BjrkAqPaQ1i1p044jNSkMvGdFtDLHxYOB07WLZfVAqD5fvIOxGQGMyUZzL7wEM5aN8es27gfAk7q%2BiYokU38%2Bg7VRHL6l0qydjGsOGbHzb4J8g4t9lIcdu6cNbJcLw8k5dvzTEalIb1nNSkEFeeZFFI2mADRd7b%2BC9erN%2FMv49IqbtbC1yD5VtNIKFexTArgUBh8Xo9rlKRoZHD2%2B3FMmSbU15%2BQSU25Gp0Nhqu7x%2FmBYlkCRgp5q3EieTAeL3MVVzouZjTS7W8e6LzTyOmic8H3KVHDa7QJ24bHabR7h4%2FHh0oTtmdzOE8i8X40cfj2FEwLs7UG7uupL3b%2F4JkVTI1lqR%2Bg8t0zI%2FzkqCQrv6wpfkl5z8RozSWhvF0HDVcWR2LzptgWmOfHnEzqPJGYguhvkih6W6ok8Ba6lLa6SBahqSh%2BKi9NnpzyZtWbC0Kgr%2But8mMNI0reHD1qd%2FwGYDy1QqMz4hRK&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIAS6J7QBRBJTD7RFXR%2F20241114%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241114T034911Z&X-Amz-Expires=43200&X-Amz-SignedHeaders=host&X-Amz-Signature=751bb4408cad9753b86ce4791ed1b6877de34b9ffecfcfcff85f7483d06f16bd";
		String userMessage = promptUtil.getPdfTPQPrompt();
		ClaudeResponse claudeResponse = claudeUtil.sendPdfMessages(pdfUrl, userMessage);
		List<String> newSentences = sentenceUtil.splitByNewline(claudeResponse.getContent().get(0).getText());

		for (String sentence : newSentences) {
			log.info("sentence: {}", sentence);
		}
	}

	public void testTPQClaudeApi() {

		String testText = promptUtil.TPQ(getTestText());
		// String testText = promptUtil.complete(getTestText());
		// String testText = promptUtil.simple(getTestText());
		ClaudeResponse claudeResponse = claudeUtil.sendMessage(testText);
		List<String> newSentences = sentenceUtil.splitByNewline(claudeResponse.getContent().get(0).getText());

		for (String sentence : newSentences) {
			log.info("sentence: {}", sentence);
		}
	}

	public void testAnswerClaudeApi() {

		String T = "스키마와 지식 습득";
		String P = "지식 습득의 기본 원리";
		String Q = "새로운 지식은 어떤 방식으로 습득되나요?";

		String testText = promptUtil.getAnswerPrompt(T, P, Q, getTestText());
		ClaudeResponse claudeResponse = claudeUtil.sendMessage(testText);
		List<String> newSentences = sentenceUtil.splitByNewline(claudeResponse.getContent().get(0).getText());

		for (String sentence : newSentences) {
			log.info("sentence: {}", sentence);
		}
	}

	public String getTestText() {

		return String.join("", "공부란 스키마로 해석‧재구성해 새로운 스키마를 만드는 것\n",
			"새로운 지식은 기존의 지식을 통해 습득된다. 사람은 기존의 지식을 근거로 새로운 지식을 이해하고 해석한다. 사실적 지식들이 개념화돼 개념적 지식을 만들게 되면, 이 개념 지식은 새로운 경험들을 해석하고 구성해 스키마 체계에 넣게 된다.\n",
			"만약 지식이 없다면 새로운 지식은 매우 낯설어, 이해하기 어려울 것이다. 이 지식을 기억하기 위한 유일한 방법은 반복일 것이다. 하지만 반복해서 기억하는 것으로는 부족하다. 반복 학습을 통해 새로운 스키마를 만들어야 제대로 공부를 했다고 할 수 있는 것이다.\n",
			"반복을 통해 새로운 지식을 개념화하는 과정은 저절로 이뤄지지 않기 때문에 의도적으로 노력해야한다. 하지만 사전 지식이 있음에도 불구하고 새로운 지식을 반복해 학습하는 것은 매우 어리석은 일이다. 새로운 지식을 기존의 지식으로 이해하고 체계화하는 것이 가장 효율적인 방법이다. 새로운 지식을 학습할 때는 먼저 자신의 지식을 탐색해 가장 관계있는 스키마로 이해해야한다. 결국 공부란 스키마로 해석하고 재구성해 새로운 스키마를 만드는 것이다.\n",
			"‘민주주의’란 개념을 대했을 때, 이 말을 처음 들었다 하더라도 이미 갖고 있는 한글에 대한 지식을 바탕으로 이 글을 읽게 된다. 비록 의미는 모르더라도 글씨를 읽을 수 있는 것은 이미 갖고 있는 지식으로 해석했기 때문이다. 따라서 ‘민주주의’와 ‘민주주의’, ‘민주주의’, ‘민주주의’는 비록 글자체는 다르지만 모두 같은 의미로 받아들인다.\n",
			"또 세상에 있는 모든 사람은 생김새가 다 다르다. 비록 일란성 쌍둥이라 하더라도 가족들은 구별할 수 있다고 하니 생김새가 같은 사람은 없다고 할 수 있겠다. 그런데 우리는 어떤 사람을 보더라도 상대가 사람이라는 것을 안다. 그럼 어떻게 알 수 있을까? 그 이유는 우리가 이미 갖고 있는 ‘사람’이라는 개념으로 해석했기 때문이다. 이와 같이 사람은 어떤 경험을 그대로 받아들이는 것이 아니라, 자신이 지니고 있는 지식, 즉 스키마로 해석하고 재구성해 받아들인다.\n",
			"공부란 새로운 경험을 받아들이는 것이라 할 수 있다. 그런데 새로운 경험을 있는 그대로 받아들이지 못하고 반드시 해석하고 재구성해 받아들인다. 따라서 공부를 잘하기 위해서는 잘 해석하고, 재구성하는 것이 중요하다.\n",
			"잘 해석하고 재구성하는 것이 중요\n",
			"그렇다면 어떻게 해석해야 할까? 학생들이 공부하는 것을 대상으로 삼아 생각해보자. 우선 우리가 배우는 것은 거의 언어로 표시된 개념적인 지식이라 할 수 있다. 그런데 어떤 언어에 대해 갖고 있는 개념은 사람마다 모두 다를 수 있다.\n",
			"예를 들어 ‘민주주의’라는 언어에 대해 갖고 있는 개념은 사람마다 모두 다를 수 있다는 것이다. 그리고 사람들은 자신의 개념으로 새로운 지식을 해석한다고 했다. 따라서 새로운 지식을 통해 형성된 언어에 대한 개념과 그것을 배우는 사람들이 지닌 개념이 다를 수 있게 된다.\n",
			"만약 이러한 상황에서 새로운 지식을 해석한다면 필연적으로 잘못 해석할 가능성이 높다. 그러므로 새로운 지식을 공부할 때, 그 지식에서 사용한 개념과 자신이 지니고 있는 개념을 일치시키는 것이 가장 중요하다. 즉 새로운 지식에 대한 이해를 먼저 해야 하는 것이다.\n",
			"새로운 지식을 공부하기 위해서는 그 지식에서 사용하는 개념의 정확한 의미를 이해한 뒤, 그 의미를 가지고 해석하는 것이 중요하다. 만약 그렇게 하지 않으면 잘못된 해석을 할 수 있기 때문이다. 이런 면에서 공부란 기존의 지식을 새롭게 하는 과정이라고도 할 수 있다.\n",
			"만약 어떤 사람이 이러한 이해나 해석을 하지 않고 공부를 한다면, 그것은 단지 글씨 공부를 하고 있는 것에 불과하다. 마치 처음 글자를 배운 어린이가 그 의미는 전혀 생각하지 않고 읽는 것과 같다고 할 수 있다. 사람은 의식하지는 못하지만 새로운 지식을 공부할 때에는 어떤 식으로든 해석하고 재구성한다고 볼 수 있다.\n");
	}

}

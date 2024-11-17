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

	public void testKeyword() {

		String testText = promptUtil.getKeywordTPQ("정렬 알고리즘");
		ClaudeResponse claudeResponse = claudeUtil.sendMessage(testText);
		List<String> newSentences = sentenceUtil.splitByNewline(claudeResponse.getContent().get(0).getText());

		for (String sentence : newSentences) {
			log.info("sentence: {}", sentence);
		}
	}

	public void testKeywordAnswer(String T, String P, String Q, String keyword) {

		String testText = promptUtil.getKeywordAnswerPrompt(T, P, Q, keyword);
		ClaudeResponse claudeResponse = claudeUtil.sendMessage(testText);
		List<String> newSentences = sentenceUtil.splitByNewline(claudeResponse.getContent().get(0).getText());

		for (String sentence : newSentences) {
			log.info("sentence: {}", sentence);
		}
	}

	public void testImage() throws Exception {

		String imageUrl = "https://shortgong.s3.us-east-1.amazonaws.com/test-upload-content/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4%EC%84%A4%EB%AA%85.PNG?response-content-disposition=inline&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHcaCXVzLWVhc3QtMSJHMEUCIFIImL0kKexduiL02DtjPGvBUBhLFQOj6lt2Hcy3qoObAiEAzhMG4cKnC4H7%2BXjvmBPxqh%2BqQNzCj69Poe8CfhgF7HMqxwMIEBAAGgwyMDI1MzM1MDYxMTQiDP4Hm0shTyYk967kYCqkAzloIxJ%2F%2BTqdUBlJQL7lg%2Bq2Pw5SmGFFolNzA9XSnXRUlUTox3B8SZ6UGdnCEW7eZBnM8cRxjSJrosFDomzgx5T4LHDB2R%2FB4z7y7vgB1fKpx%2B6WWfa6sQlZp8ZidPES9pEU7XOUnmdLFL8tCRvQbwx7TsxLR%2BRdj3F9kak7qoT%2FtxBQODQbWqG%2F%2ByPLlFUw3ds%2FWfyPEga6%2Bv3KqHfGa3H1q7BNrKUduiYP9MJ6jC1iBHWIlMNLz5xKtmu%2BMp5a3Fn0s1QX5waoakoOMbLJ9rKdGRKXdkJ%2FD0%2FXpj9nwLc6%2FnS2w1Eksjkhhc%2BfzTCVAHi%2FU1SbL1KkfDU43Z8RRiigdpzrXtaRJp2dsYkDwjBjBXjfrWb3D87aMEHd8hacYstNtwN%2FZHMp4D2dCUMN6TMPuGoxmm4QhyMqgZRoHpZYmNrZWeo8AZDZgQKnFc4Ogmwlc4sce5kGY7bKzzI1%2Bmy14rjGXkIQx84zN%2FU3KwlW2PM8f9qiHC%2Fl9SZrC8yg59BQS%2FQom%2F5wVtT9zGHQ33k93cpYwhH9b8zhPK2CpDmvAqM8rjC74Nq5BjrkAlDRC4KkFuBDmjjVf1itdHFuhoQXveBKbnoTMM14Ey9x%2FagaC9xOGkT6UgSjasdBWvxcCmZqKjwtZmM8LRWQX5qCWo1NUCvV5Q9LS8NzBIpyYNa4AVx%2BXXFRD1mnyEUG%2BbqFK6Zzjgqr8IALY5pDGKo6WhacV7AeUYasDLplxP2ccZqFZMLnnsqB6q5lmGwSlXca%2BdksYzLmEl7CXKHBUgvRf6jQwbceJ4XIfM2oFFZQnSBaSW1ZquPouQRQw6ZhzHHo2gWrGddKcCZjGlFxI0UUZdLViDcHf6YQkxdXMpwDmFIZ9bmvmSF3NQgWvzQmBUnEWeFcM8huPjRd2O4ZZywd%2FKMTXO616gjFhP%2F3Kr3NINIk2YkSgCUOJiuSW3%2F2DWX6OPMffhai5%2BLPGXpnnzxRcNt0dJmCcVoqDXqDWYN0FDQugrnEJPFeB%2Bip1EYm75KIAszKiA2rsleBHFuDTMilPDl%2F&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIAS6J7QBRBFNPHPXXV%2F20241115%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241115T065421Z&X-Amz-Expires=43200&X-Amz-SignedHeaders=host&X-Amz-Signature=9df767cfb2fac3ac9c0ea9907f4685a38076173f1418b2b9249b44696cb870e1";
		String userMessage = promptUtil.getPdfImageTPQPrompt();
		ClaudeResponse claudeResponse = claudeUtil.sendImageMessages(imageUrl, userMessage);
		List<String> newSentences = sentenceUtil.splitByNewline(claudeResponse.getContent().get(0).getText());

		for (String sentence : newSentences) {
			log.info("sentence: {}", sentence);
		}
	}

	public void testPdf() throws Exception {

		String pdfUrl = "https://shortgong.s3.us-east-1.amazonaws.com/test-upload-content/%ED%95%80%ED%85%8C%ED%81%AC_%ED%8A%B9%EA%B0%95.pdf?response-content-disposition=inline&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHMaCXVzLWVhc3QtMSJIMEYCIQD9RGw1AK8CHbq5K4ok7YDFNjj74If34AhWbf6MNilOcAIhANKMHAYyLjS9lLlhSsWM%2FEdCJxpGp%2FR9zMS1DDmZNRujKtADCPz%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEQABoMMjAyNTMzNTA2MTE0IgwF7vWPPm%2BOFaTu3bIqpAOkJ6Qayicmk4bqVcENlctwMG%2FpsQU5ZBJBYRh0RckZU1h47rBYBhgZNVa069VNAiNgb0uVU9AWpMWmMlqlSalosBBH8O8D7uzcnvFtxvDLimS%2Bc8jSaVFstppHUU4lm5guVH%2BCTwLIRhPjSkfRYfOq9m1kmgget9rVRIKQ62V3Knm2tsSYdEZ2%2F5Zy5Bd3PKbIPtkGERJlosROtv9S6XuOICMqfe0J5FEXyax85gmlCoKyNpPcxG5HoG4jocHozL878sVOAQjb1I5yuePIL6nOPGDudkqnlFpk3%2B%2Fxs33sCGvRv0ecBBKfOiyfBXuS4FO1sIo8BzKqk83AQG%2BC%2B1T5A9q0OlT9J74yPyJItieAn%2F6kqAOuHPZ7plHavmQYvNKEilpEcIWPi1oJ5ktbK52XVX1J697INawfOfDYndMkuoiErBYcSgMQMd5yTgz95k%2ByNl7Vx%2BWpP6p8CTQN9npe%2FF8C7nK5T4IglGxxE4ltNHt3hfkflX%2Fzi9HJUiKnaBbZWChRWML3UT%2FJuTzu8C4qrYOkzIdRgNeSZe8pQDirBN2Ca4Ywu%2BDauQY64wL4FP9XFc6tBdxh7wJmd76BzSUwdpqZaRlqDKp%2B45F45wcy%2FNRWFUfqwfAtjyVSy01trhd9sh%2BkhuvWxkj%2FI%2BluBCyAgLjOSW8zNq5ezQawkhS7hJ2hDXkfA3AJr1m3ka2OrgG%2BdhSOPxFiRFfdFVa2zlL2u8q3EHeMTvy%2BfypKMiYcBMv3mvK3BKEBvDwhCFepiVu9ncUHDaKlNhcvKHB91y5jgvdzC8ki%2B2vOPcwmFTk%2F9S55eqYcVzQEa%2BmnlutpirFJbrT9EJMXh2IU%2FCH%2F8UNqvQEq7wMNXxReVo3Okyl7jTOJ4hYWa6A5aWxKMZ7NZTJB2hvtBzbqpwutZOPZJfK11op4lulVPMozp7Bvgh5cPdfmfxu7Om2jFsFuwI%2FfWA32YS8d99H%2BEVcaPDEpOXp6uqvCbDW1vrXUhr0CLWMnGFUtakTFCDNmQZEwGBph6Gf6eMI5twdpYuSrXDmiK0br&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIAS6J7QBRBDGAJYWM6%2F20241115%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20241115T022220Z&X-Amz-Expires=43200&X-Amz-SignedHeaders=host&X-Amz-Signature=cc29fc6ec4b89445f18849a816bdccf88b5a273faf0fe014b8f7cc7784d8b9dd";
		String userMessage = promptUtil.getPdfImageTPQPrompt();
		ClaudeResponse claudeResponse = claudeUtil.sendPdfMessages(pdfUrl, userMessage);
		List<String> newSentences = sentenceUtil.splitByNewline(claudeResponse.getContent().get(0).getText());

		for (String sentence : newSentences) {
			log.info("sentence: {}", sentence);
		}
	}

	public void testTPQClaudeApi() {

		String testText = promptUtil.TPQ(getTestText());
		sendMessage(testText);
	}

	public void testAnswerClaudeApi() {

		String T = "스키마와 지식 습득";
		String P = "지식 습득의 기본 원리";
		String Q = "새로운 지식은 어떤 방식으로 습득되나요?";

		String testText = promptUtil.getAnswerPrompt(T, P, Q, getTestText());
		sendMessage(testText);
	}

	private void sendMessage(String testText) {

		claudeUtil.sendMessageAsync(testText, new ClaudeUtil.Callback() {
			@Override
			public void onSuccess(ClaudeResponse response) {

				List<String> newSentences = sentenceUtil.splitByNewline(response.getContent().get(0).getText());

				for (String sentence : newSentences) {
					log.info("sentence: {}", sentence);
				}
			}

			@Override
			public void onError(Exception e) {

				log.error("Error: {}", e.getMessage());
			}
		});
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

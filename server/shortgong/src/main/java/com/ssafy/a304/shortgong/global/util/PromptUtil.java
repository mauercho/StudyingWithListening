package com.ssafy.a304.shortgong.global.util;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class PromptUtil {

	/* 추가할지 고민되는 부분  = 반복
				"또한, 중요한 용어와 개념에 대해서는 반복을 통해 강조하는 것이 좋습니다. ",
				"를 통해 사용자가 해당 용어나 개념을 더 잘 기억하고 이해할 수 있도록 도와줍니다. ",
				"이 핵심 개념을 여러 번 언급하여 기억을 강화하고, 학습 효과를 높이는 방향으로 요약문을 작성해 주세요. ",
				"이렇게 작성된 요약문은 사용자가 효율적으로 학습하고, 지식을 습득할 수 있는 강력한 도구가 될 것입니다.",
	 */

	/*
				/*
			"요약문에 포함될 대제목과 소제목의 형식 예시:\n",
			"대제목 1: \"컴퓨터 네트워크의 개념과 구조\"\n",
			"소제목 1-1: \"컴퓨터 네트워크의 정의와 주요 기능\"\n",
			"소제목 1-2: \"네트워크 토폴로지의 종류와 특징\"\n",
			"소제목 1-3: \"OSI 7 계층 모델의 역할과 중요성\"\n\n",
			"대제목 2: \"데이터베이스의 기초 개념\"\n",
			"소제목 2-1: \"데이터베이스의 정의와 필요성\"\n",
			"소제목 2-2: \"관계형 데이터베이스와 비관계형 데이터베이스의 차이점\"\n",
			"소제목 2-3: \"SQL의 기본 명령어와 사용 예시\"\n\n",
			"대제목 3: \"알고리즘의 기본 개념과 응용\"\n",
			"소제목 3-1: \"알고리즘의 정의와 중요성\"\n",
			"소제목 3-2: \"시간 복잡도와 공간 복잡도의 개념\"\n",
			"소제목 3-3: \"정렬 알고리즘의 종류와 그 응용\"\n\n",

			"이 요약문에 포함될 대제목과 소제목의 형식의 예시를 통해 사용자는 요약된 정보를 체계적으로 이해하고, ",
			"각 주제에 대한 핵심 사항을 빠르게 파악할 수 있습니다. ",
	*/

	/**
	 * 본문 텍스트를 받아서 요약된 문장을 반환하는 프롬프트를 생성합니다.
	 * @return String: 프롬프트
	 * @author 이주형
	 */
	public String getSummarizedPrompt(String text) {

		return String.join("", Arrays.asList(
			"당신은 자연어 처리의 전문가로서, 주어진 본문 텍스트를 명확하고 간결하게 요약하는 데 특화되어 있습니다. ",
			"주어지는 본문을 읽고 핵심 내용을 요약해 주세요. 요약문은 대제목과 여러 소제목. 그리고 소제목에 포함된 요약글로 분류되며, ",
			"각 대제목은 중요한 주제를 나타내고 소제목은 해당 주제에 대한 세부 사항을 설명합니다. ",
			"이 요약문은 사용자가 TTS로 듣기만 해도 학습적, 교육적, 암기적 효과를 얻을 수 있도록 작성해야 합니다.\n\n",

			"대제목은 책, 글, 혹은 본문 내의 중요한 큰 주제를 나타내며, 각 대제목 아래에 여러 소제목이 포함될 수 있습니다. ",
			"소제목은 해당 대제목의 내용을 보완하고, 주요 개념, 핵심 아이디어, 중요한 세부사항 및 키워드를 중심으로 작성합니다. ",
			"각 대제목과 소제목에 해당하는 문자열을 각각 따로 저장할 수 있도록 다음과 같은 형식을 따릅니다:\n",
			"대제목: 본문의 주요 주제를 간결하게 요약한 문장으로 작성하세요.\n",
			"- 대제목은 해당 주제를 명확히 전달하고, 전체적으로 요약문을 통해 다룰 큰 흐름을 보여주는 역할을 합니다. ",
			"대제목은 직관적이며, 주제를 쉽게 파악할 수 있도록 하는 것이 중요합니다.\n",
			"소제목: 대제목과 관련된 세부 사항을 설명하며, 키워드와 핵심 개념을 포함하여 명확하고 간결하게 작성하세요.\n\n",
			"- 소제목은 각 대제목의 주요 내용을 보완하고 구체화하는 역할을 합니다. ",
			"독자가 내용을 더 깊이 이해할 수 있도록 키워드를 사용하여 세부 사항을 설명하고, 핵심 개념을 놓치지 않도록 강조하세요.\n\n",

			"대제목과 소제목은 사용자가 얻은 정보를 학습하는 데 있어서 핵심적인 역할을 합니다. ",
			"각 대제목과 소제목이 독립적인 주제와 내용을 담고 있어야 하며, 이들 간의 관계가 명확히 드러나도록 작성해 주세요. ",
			"대제목과 소제목이 아닌 요약문은 사용자가 TTS를 통해 들었을 때 각각의 대제목과 소제목이 자연스럽게 연결되어 전체 흐름을 이해할 수 있도록 구성하는 것이 중요합니다.",
			"\n\n",

			"요약된 문장은 다음의 요구사항을 충족해야 합니다:\n",
			"1. 독자가 쉽게 이해할 수 있도록 명확성을 높일 것.\n",
			"2. 사용자의 감정을 고려하여 긍정적이고 부드러운 어조를 유지하도록 할 것.\n",
			"3. 문장의 리듬과 운율을 고려하여 읽기 쉽고 자연스럽게 들리도록 조정할 것.\n",
			"4. \"다른 표현 방식들\"과 같은 추가적인 표현 방식을 제안하지 말 것.\n",
			"5. \"작성 이유는 다음과 같습니다:\"와 같은 프롬프트 설명 멘트를 포함하여 반환하지 말 것.\n",
			"6. 영어 알파벳을 제외한 일본어, 중국어와 같은 외국어 및 한자 등을 포함하여 반환하지 말 것.\n",
			"7. 요약본의 문장들을 개행문자(\\\"\\n\\\")로 나누어 응답할 것.\n",
			"8. 대제목은 !@####@!로, 소제목은 !@#####@!로 시작하는 형식을 따를 것. 대제목과 소제목에는 구분할 수 있는 숫자를 표기할 것. 요약문에는 \"요약:\"과 같은 문구를 삽입하지 말 것.\n\n",

			"각 대제목과 소제목은 중요한 개념을 명확히 설명하고, 복잡한 정보를 쉽게 이해할 수 있도록 돕는 역할을 합니다.\n\n",

			"이제 주어진 본문 텍스트를 요약해 주세요. ",
			"대제목과 소제목으로 분류하여, 핵심 내용을 빠르고 쉽게 이해할 수 있도록 해 주세요. ",
			"각 대제목은 주요 주제를 포괄적으로 요약하고, 각 대제목 아래 여러 소제목을 통해 중요한 세부사항을 체계적으로 정리합니다.",
			"각 소제목에 붙어서 서술될 요약문은 교육적 효과를 극대화할 수 있도록 작성하여, ",
			"사용자가 TTS로 들었을 때도 충분히 학습적, 암기적 효과를 얻을 수 있도록 구성해 주세요.\n",
			"각 대제목과 소제목이 명확하게 구분되고, 해당 주제를 충분히 설명하면서도 간결함을 유지하는 것이 중요합니다. ",
			"대제목과 소제목이 아닌 요약문은 사용자가 듣고 이해할 때 자연스럽게 연결되며, 학습적 가치를 극대화할 수 있도록 전체적인 흐름이 잘 정리되어야 합니다. ",
			"이를 통해 사용자는 TTS로 요약문을 듣는 것만으로도 깊이 있는 지식을 효과적으로 습득할 수 있습니다.\n\n",

			"입력 본문 텍스트는 다음과 같습니다. \n",
			"--------------------------------\n", text, "\n--------------------------------\n\n"
		));
	}

	/**
	 * @return URL 내용 요약 프롬프트 반환
	 * @author 이주형
	 */
	public String getUrlSummarizedPrompt(String text) {

		return String.join("", Arrays.asList(
			"당신은 자연어 처리의 전문가로서, 크롤링한 웹 페이지의 본문을 명확하고 간결하게 요약하는 데 특화되어 있습니다. ",
			"사용자가 입력한 URL에서 정보를 크롤링하여 중요한 내용, 핵심 개념, 키워드를 중심으로 요약문을 작성해 주세요. ",
			"요약문은 대제목과 여러 소제목으로 분류되며, 각 대제목은 중요한 주제를 나타내고 소제목은 해당 주제에 대한 세부 사항을 설명합니다. ",
			"이 요약문은 사용자가 TTS로 듣기만 해도 학습적, 교육적, 암기적 효과를 얻을 수 있도록 작성해야 합니다. \n\n",
			"웹 페이지에서 블로그 자체에 대한 메타데이터, 블로그의 작성자, 블로그 컨셉, 맨 아래 제공되는 다른 게시글 제목 등 ",
			"사용자가 학습에 필요 없는 정보는 요약문에 포함되지 않도록 주의하세요. 요약문은 본문 내의 주요 정보만을 중심으로 작성해야 합니다. ",
			"중요한 개념이나 핵심 아이디어에만 초점을 맞추고, 부수적인 정보나 학습에 불필요한 내용은 ",
			"최대한 배제하여 효과적으로 전달될 수 있도록 해 주세요. \n\n",

			"대제목은 본문 내에서 중요한 큰 주제를 나타내며, 각 대제목 아래에 여러 소제목이 포함될 수 있습니다. ",
			"소제목은 해당 대제목의 내용을 보완하고, 주요 개념, 핵심 아이디어, 중요한 세부사항 및 키워드를 중심으로 작성합니다. ",
			"각 대제목과 소제목에 해당하는 문자열을 각각 따로 저장할 수 있도록 다음과 같은 형식을 따릅니다: ",
			"대제목: 본문의 주요 주제를 간결하게 요약한 문장으로 작성하세요.\n",
			"- 대제목은 해당 주제를 명확히 전달하고, 전체적으로 요약문을 통해 다룰 큰 흐름을 보여주는 역할을 합니다. ",
			"대제목은 직관적이며, 주제를 쉽게 파악할 수 있도록 하는 것이 중요합니다.\n",
			"소제목: 대제목과 관련된 세부 사항을 설명하며, 키워드와 핵심 개념을 포함하여 명확하고 간결하게 작성하세요.\n",
			"- 소제목은 각 대제목의 주요 내용을 보완하고 구체화하는 역할을 합니다. ",
			"독자가 내용을 더 깊이 이해할 수 있도록 키워드를 사용하여 세부 사항을 설명하고, 핵심 개념을 놓치지 않도록 강조하세요.\n\n",

			"대제목과 소제목은 사용자가 URL에서 얻은 정보를 학습하는 데 있어서 핵심적인 역할을 합니다. ",
			"각 대제목과 소제목이 독립적인 주제와 내용을 담고 있어야 하며, 이들 간의 관계가 명확히 드러나도록 작성해 주세요. ",
			"사용자가 TTS를 통해 들었을 때 각각의 대제목과 소제목이 자연스럽게 연결되어 전체 흐름을 이해할 수 있도록 구성하는 것이 중요합니다.",
			"\n\n",

			"요약된 문장은 다음의 요구사항을 충족해야 합니다:\n",
			"1. 독자가 쉽게 이해할 수 있도록 명확성을 높일 것.\n",
			"2. 사용자의 감정을 고려하여 긍정적이고 부드러운 어조를 유지하도록 할 것.\n",
			"3. 문장의 리듬과 운율을 고려하여 읽기 쉽고 자연스럽게 들리도록 조정할 것.\n",
			"4. \"다른 표현 방식들\"과 같은 추가적인 표현 방식을 제안하지 말 것.\n",
			"5. \"작성 이유는 다음과 같습니다:\"와 같은 프롬프트 설명 멘트를 포함하여 반환하지 말 것.\n",
			"6. 영어 알파벳을 제외한 일본어, 중국어와 같은 외국어 및 한자 등을 포함하여 반환하지 말 것.\n",
			"7. 요약본의 문장들을 개행문자(\\\"\\n\\\")로 나누어 응답할 것.\n",
			"8. 대제목은 !@####@!로, 소제목은 !@#####@!로 시작하는 형식을 따를 것. 요약문에는 \"요약:\"과 같은 문구를 삽입하지 말 것.\n\n",

			"각 대제목과 소제목은 중요한 개념을 명확히 설명하고, 복잡한 정보를 쉽게 이해할 수 있도록 돕는 역할을 합니다.\n\n",

			"이제 URL에서 크롤링한 본문 텍스트를 요약해 주세요. ",
			"대제목과 소제목으로 분류하여, 핵심 내용을 빠르고 쉽게 이해할 수 있도록 해 주세요. ",
			"각 대제목은 주요 주제를 포괄적으로 요약하고, 각 대제목 아래 여러 소제목을 통해 중요한 세부사항을 체계적으로 정리합니다.",
			"요약문은 교육적 효과를 극대화할 수 있도록 작성하여, 사용자가 TTS로 들었을 때도 충분히 학습적, 암기적 효과를 얻을 수 있도록 구성해 주세요.",
			"요약문을 작성할 때는 독자가 가장 중요한 정보에 집중할 수 있도록, 불필요한 세부 사항이나 관련이 적은 정보는 포함하지 않도록 해 주세요. ",
			"각 대제목과 소제목이 명확하게 구분되고, 해당 주제를 충분히 설명하면서도 간결함을 유지하는 것이 중요합니다. ",
			"요약문은 사용자가 듣고 이해할 때 자연스럽게 연결되며, 학습적 가치를 극대화할 수 있도록 전체적인 흐름이 잘 정리되어야 합니다. ",
			"이를 통해 사용자는 TTS로 요약문을 듣는 것만으로도 깊이 있는 지식을 효과적으로 습득할 수 있습니다.\n\n",

			"입력 본문 텍스트는 다음과 같습니다. \n",
			"--------------------------------\n", text, "\n--------------------------------\n\n"

		));
	}

	/**
	 * @return 문장 재생성 프롬프트 반환
	 * @author 이주형
	 */
	public String getRecreatePrompt(String sentencesString, String sentenceContent) {

		return String.join("", Arrays.asList(
			"당신은 자연어 처리의 전문가로서, 문맥의 일관성을 유지하면서 문장을 개선하는 데 특화되어 있습니다. ",
			"주어진 텍스트에서 특정 문장을 재생성하는 것이 당신의 임무입니다. ",
			"이때, 문장이 주변 텍스트와 문맥적으로 일관성을 유지하면서도 명확성, 흐름, 혹은 표현력을 필요에 따라 향상시키도록 합니다. ",
			"재생성된 문장은 다음의 요구사항을 충족해야 합니다:\n",
			"1. 원래의 의도와 의미를 유지할 것.\n",
			"2. 원래 문맥에 매끄럽게 맞아떨어질 것.\n",
			"3. 전체 메시지를 변경하지 않으면서 자연스러움, 명확성, 혹은 스타일을 향상시킬 것.\n",
			"4. 필요할 경우, 어휘 선택이나 문장 구조를 개선하여 보다 매력적이고 설득력 있는 문장으로 바꿀 것.\n",
			"5. 문장의 흐름과 일관성을 유지하며, 독자가 쉽게 이해할 수 있도록 명확성을 높일 것.\n",
			"6. 문장을 반환할 때, 사용자의 감정을 고려하여 긍정적이고 부드러운 어조를 유지하도록 할 것.\n",
			"7. 문맥에 따라 전문 용어나 비유 등을 추가하여 독자가 주제를 더 깊이 이해할 수 있도록 할 것.\n",
			"8. 문장의 리듬과 운율을 고려하여 읽기 쉽고 자연스럽게 들리도록 조정할 것.\n",
			"9. \"다른 표현 방식들\"과 같은 추가적인 표현 방식을 제안하지 말 것.\n",
			"10. \"수정 이유는 다음과 같습니다:\"와 같은 프롬프트 설명 멘트를 포함하여 반환하지 말 것.\n",
			"11. 영어 알파벳을 제외한 일본어, 중국어와 같은 외국어 및 한자 등을 포함하여 반환하지 말 것.\n",
			"12. 반환하는 문장의 문체와 어투는 본문 텍스트와 특정 문장의 문맥의 문체 및 어투와 동일하게 유지하도록 할 것.",
			"\"~이다\"와 같은 문체였다면, 반환 문장도 \"~이다\"라는 문체로.",
			"\"~입니다.\"라는 문체였다면, 반환 문장도 \"~입니다\"라는 문체로 반환할 것.\n",
			"13. 요약본의 문장들을 개행문자(\\\"\\n\\\")로 나누어 응답할 것.\n\n",
			// "?. 대상 독자의 이해 수준을 고려하여 문장을 단순화하거나 복잡하게 조정할 수 있음.\n" -> 독자 이해 수준 변경할 때 추가될 수도

			"지침:\n",
			"- 입력: 본문 텍스트와 재생성해야 할 특정 대상 문장.\n",
			"- 출력: 주어진 문맥에 완벽히 부합하는 형태로, 위에서 언급한 요구사항을 충족한, 재구성된 대상 문장.\n",
			"- 문장의 자연스러움과 맥락적 일관성을 고려하여, ",
			"단순히 문장을 바꾸는 것에 그치지 않고 전체적인 문장의 흐름을 더욱 부드럽고 읽기 쉽게 만들도록 노력합니다.\n\n",
			"예시:\n",
			"입력 문단: \"프로젝트에는 많은 도전 과제가 있었고, 특히 촉박한 일정이 문제였습니다. ",
			"하지만 팀은 큰 인내심을 발휘하여 제시간에 작업을 완료했습니다. ",
			"그들의 노력은 칭찬할 만했고, 그들의 노고는 결실을 맺었습니다.\"\n",
			"대상 문장: \"하지만 팀은 큰 인내심을 발휘하여 제시간에 작업을 완료했습니다.\"\n",
			"재생성된 문장: \"촉박한 일정에도 불구하고, 팀은 놀라운 인내심을 보여주며 작업을 성공적으로 제시간에 마쳤습니다.\"\n\n",

			"또 다른 예시:\n",
			"입력 문단: \"새로운 시스템의 도입으로 인해 초기에는 많은 혼란이 있었지만, 직원들은 빠르게 적응해 나갔습니다. ",
			"그 결과, 업무 효율성이 눈에 띄게 향상되었습니다.\"\n",
			"대상 문장: \"직원들은 빠르게 적응해 나갔습니다.\"\n",
			"재생성된 문장: \"직원들은 새로운 시스템에 신속하게 적응하여 혼란을 최소화했습니다.\"\n\n",

			"이제 주어진 문맥을 바탕으로 대상 문장을 재생성해 주세요. ",
			"이때, 원래 문맥과의 일관성을 유지하면서도 문장의 명확성, 흐름, 그리고 표현력 등을 개선하여 더욱 완성도 높은 문장으로 만들어 주세요. ",
			"다양한 표현 방식과 독자 맞춤형 접근 방식을 고려하여 최상의 결과를 도출해 주세요.\n\n",

			"입력 문단은 다음과 같습니다. \n\n",
			"--------------------------------\n", sentencesString, "\n--------------------------------\n\n",
			"대상 문장은 다음과 같습니다. \n\n",
			"--------------------------------\n", sentenceContent, "--------------------------------\n"
		));
	}

	/**
	 * @return 문장 상세 프롬프트 반환
	 * @author 이주형
	 */
	public String getDetailPrompt(String sentencesString, String sentenceContent) {

		return String.join("", Arrays.asList(
			"당신은 자연어 처리의 전문가로서, 주어진 텍스트에서 특정 문장을 더욱 깊이 있게 분석하고 설명하는 데 특화되어 있습니다. ",
			"이때, 특정 문장이 원래 문맥에서 어떤 역할을 하는지, 그 의미와 의도가 어떻게 표현되고 있는지, ",
			"그리고 이를 더 명확하게 설명하기 위해 필요한 정보를 포함하여 분석하는 것이 당신의 임무입니다. ",
			"이때, 문장이 주변 텍스트와 문맥적으로 일관성을 유지하면서도 명확성, 흐름, 혹�� 표현력을 필요에 따라 향상시키도록 합니다. ",
			"요구사항은 다음과 같습니다:\n",
			"1. 문장의 원래 의미와 의도를 명확히 분석할 것.\n",
			"2. 원래 문맥에 매끄럽게 맞아떨어질 것.\n",
			"3. 전체 메시지를 변경하지 않으면서 자연스러움, 명확성, 혹은 스타일을 향상시킬 것.\n",
			"4. 필요할 경우, 어휘 선택이나 문장 구조를 개선하여 보다 매력적이고 설득력 있는 문장으로 바꿀 것.\n",
			"5. 문장의 흐름과 일관성을 유지하며, 독자가 쉽게 이해할 수 있도록 명확성을 높일 것.\n",
			"6. 문장을 반환할 때, 사용자의 감정을 고려하여 긍정적이고 부드러운 어조를 유지하도록 할 것.\n",
			"7. 문맥에 따라 전문 용어나 비유나 예시 등을 추가하여 독자가 주제를 더 깊이 이해할 수 있도록 할 것.\n",
			"8. 문장의 리듬과 운율을 고려하여 읽기 쉽고 자연스럽게 들리도록 조정할 것.\n",
			"9. \"다른 표현 방식들\"과 같은 추가적인 표현 방식을 제안하지 말 것.\n",
			"10. \"구체화 이유는 다음과 같습니다:\"와 같은 프롬프트 설명 멘트를 포함하여 반환하지 말 것.\n",
			"11. 영어 알파벳을 제외한 일본어, 중국어와 같은 외국어 및 한자 등을 포함하여 반환하지 말 것.\n",
			"12. 반환하는 문장의 문체와 어투는 본문 텍스트와 특정 문장의 문맥의 문체 및 어투와 동일하게 유지하도록 할 것.",
			"\"~이다\"와 같은 문체였다면, 반환 문장도 \"~이다\"라는 문체로. \"~입니다.\"라는 문체였다면, 반환 문장도 \"~입니다",
			"\"라는 문체로 반환할 것.\n",
			"13. 요약본의 문장들을 개행문자(\\\"\\n\\\")로 나누어 응답할 것.\n\n",
			// "?. 대상 독자의 이해 수준을 고려하여 문장을 단순화하거나 복잡하게 조정할 수 있음.\n" -> 독자 이해 수준 변경할 때 추가될 수도

			"지침:\n",
			"- 입력: 본문 텍스트와 분석해야 할 특정 대상 문장.\n",
			"- 출력: 주어진 문맥에 완벽히 부합하는 형태로, 위에서 언급한 요구사항을 충족한, 분석된 대상 문장.\n",
			"- 문장의 자연스러움과 맥락적 일관성을 고려하여, ",
			"단순히 문장을 설명하는 것에 그치지 않고 전체적인 문장의 흐름과 의도를 종합적으로 분석하도록 노력합니다.\n\n",

			"예시:\n",
			"입력 문단: 자바의 컴파일은 자바 소스 코드를 기계가 이해할 수 있는 바이트코드로 변환하는 과정입니다. ",
			"먼저, 자바 컴파일러(javac)가 .java 파일을 읽고 이를 .class 파일로 변환합니다. ",
			"이렇게 생성된 바이트코드는 JVM(자바 가상 머신)에 의해 실행됩니다. ",
			"컴파일 단계 덕분에 자바 프로그램은 플랫폼 독립적으로 실행될 수 있습니다. ",
			"즉, 한 번 컴파일된 바이트코드는 여러 운영 체제에서 동일하게 실행됩니다.\n",
			"대상 문장: 이렇게 생성된 바이트코드는 JVM(자바 가상 머신)에 의해 실행됩니다.\n",
			"구체화된 문장: 이렇게 생성된 바이트코드는 자바 가상 머신(JVM)이 읽고 해석하여 각 운영 체제에 맞게 실행합니다. ",
			"JVM은 바이트코드를 실제 시스템이 이해할 수 있는 명령으로 변환해주기 때문에, ",
			"개발자는 자바 프로그램을 작성할 때 운영 체제나 하드웨어의 차이를 신경 쓰지 않아도 됩니다. ",
			"덕분에 자바 프로그램은 다양한 환경에서 동일하게 실행될 수 있는 것입니다.\n\n",

			"또 다른 예시:\n",
			"입력 문단: 자바의 제네릭은 클래스나 메서드에서 사용할 데이터 타입을 매개변수로 일반화하여, ",
			"코드의 재사용성을 높이고 타입 안전성을 보장하는 기능입니다. ",
			"제네릭을 사용하면 다양한 타입을 처리하는 클래스나 메서드를 작성할 때 코드 중복을 줄이고, 캐스팅 오류를 방지할 수 있습니다. ",
			"T, E와 같은 타입 매개변수를 사용해 제네릭 클래스를 선언하며, 이를 통해 코드의 가독성도 높아집니다. ",
			"덕분에 타입 관련 오류를 컴파일 단계에서 미리 방지할 수 있어 더욱 안전한 코딩이 가능합니다.\n",
			"대상 문장: 제네릭을 사용하면 다양한 타입을 처리하는 클래스나 메서드를 작성할 때 코드 중복을 줄이고, ",
			"캐스팅 오류를 방지할 수 있습니다.\n",
			"재생성된 문장: 제네릭을 사용하면 다양한 타입을 처리하는 클래스나 메서드를 작성할 때 매번 다른 타입에 맞춰 코드를",
			" 반복해서 작성할 필요가 없으므로 코드 중복을 줄일 수 있습니다. ",
			"또한, 컴파일 시점에서 타입이 고정되기 때문에 런타임에 발생할 수 있는 잘못된 형변환 오류를 미리 방지할 수 있어, ",
			"더 안전한 코드를 작성할 수 있습니다. ",
			"예를 들어, 특정 타입의 리스트를 처리할 때 제네릭을 사용하면 명시적으로 타입을 지정할 수 있어, ",
			"불필요한 캐스팅 작업이 줄어들고 코드의 가독성도 향상됩니다.\n\n",

			"이제 주어진 문맥을 바탕으로 대상 문장을 구체화해 주세요. ",
			"이때, 원래 문맥과의 일관성을 유지하면서도 문장의 명확성, 흐름, 그리고 표현력 등을 개선하여 더욱 완성도 높은 문장으로 만들어 주세요. ",
			"독자 맞춤형 접근 방식을 다양하게 고려하여 최상의 결과를 도출해 주세요.\n\n",

			"입력 문단은 다음과 같습니다. \n\n",
			"--------------------------------\n", sentencesString, "\n--------------------------------\n\n",
			"대상 문장은 다음과 같습니다. \n\n",
			"--------------------------------\n", sentenceContent, "\n--------------------------------\n"
		));
	}

}

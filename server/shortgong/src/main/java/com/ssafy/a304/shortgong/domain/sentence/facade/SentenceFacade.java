package com.ssafy.a304.shortgong.domain.sentence.facade;

public interface SentenceFacade {

	/* GPT API로 보낼 프롬프트를 조합해서 생성하는 메서드 */
	String makePrompt(Long sentenceId) throws Exception;
}

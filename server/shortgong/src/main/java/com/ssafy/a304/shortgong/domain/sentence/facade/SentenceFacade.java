package com.ssafy.a304.shortgong.domain.sentence.facade;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;

public interface SentenceFacade {

	SentencesCreateResponse executeGptApi(Long sentenceId) throws Exception;

	/* GPT API로 보낼 프롬프트를 조합해서 생성하는 메서드 */
	String makePrompt(Long sentenceId) throws Exception;

}

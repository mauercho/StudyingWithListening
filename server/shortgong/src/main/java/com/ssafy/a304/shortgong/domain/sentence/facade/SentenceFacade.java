package com.ssafy.a304.shortgong.domain.sentence.facade;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;

public interface SentenceFacade {

	/**
	 * @apiNote 문장 재생성
	 * @return SentencesCreateResponse (재생성된 문장을 리스트로 감싼 Dto)
	 * @author 이주형
	 */
	SentencesCreateResponse recreateSentence(Long sentenceId) throws Exception;

}

package com.ssafy.a304.shortgong.domain.sentence.facade;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.request.SentenceModifyRequest;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.request.SentenceUpdateOpenStatusRequest;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;

public interface SentenceFacade {

	/**
	 * @apiNote 문장 수정 분기 메서드 -> sentenceModifyRequest의 isDetail 값에 따라 분기
	 * @return SentencesCreateResponse (재생성된 문장을 리스트로 감싼 Dto)
	 * @author 이주형
	 */
	SentencesCreateResponse modifySentence(Long sentenceId, SentenceModifyRequest sentenceModifyRequest) throws
		Exception;

	void updateSentenceOpenStatus(Long sentenceId, SentenceUpdateOpenStatusRequest sentenceUpdateOpenStatusRequest);

}
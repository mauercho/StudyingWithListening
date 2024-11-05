package com.ssafy.a304.shortgong.domain.sentence.facade;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;

public interface SentenceFacade {

	SentencesCreateResponse recreateSentence(Long sentenceId) throws Exception;

}

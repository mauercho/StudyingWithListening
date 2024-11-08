package com.ssafy.a304.shortgong.domain.index.service;

import com.ssafy.a304.shortgong.domain.index.model.dto.request.IndexCreateRequest;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;

public interface IndexService {

	void createIndex(Summary summary, Sentence sentence, IndexCreateRequest request);

}

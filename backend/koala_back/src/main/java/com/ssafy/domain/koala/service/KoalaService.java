package com.ssafy.domain.koala.service;

import com.ssafy.domain.koala.model.dto.request.KoalaNameRequest;
import com.ssafy.domain.koala.model.dto.response.KoalaResponse;

public interface KoalaService {

	KoalaResponse getKoala();

	KoalaResponse changeKoalaName(KoalaNameRequest koalaNameRequest, Long koalaId);

	KoalaResponse increaseKoalaExp(Long koalaId);

	void addKoala(Long userId);

}

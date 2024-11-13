package com.ssafy.a304.shortgong.domain.summary.service;

import static com.ssafy.a304.shortgong.global.errorCode.SummaryErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.summary.model.dto.response.SummaryOverviewResponse;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.summary.repository.SummaryRepository;
import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;
import com.ssafy.a304.shortgong.global.error.CustomException;
import com.ssafy.a304.shortgong.global.util.CrawlingServerConnectUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SummaryServiceImpl implements SummaryService {

	private final CrawlingServerConnectUtil crawlingServerConnectUtil;
	private final SummaryRepository summaryRepository;

	@Override
	public Summary selectSummaryById(Long id) throws CustomException {

		return summaryRepository.findById(id).orElseThrow(() -> new CustomException(SUMMARY_FIND_FAIL));
	}

	@Override
	@Transactional
	public Summary createNewSummary(User loginUser, UploadContent uploadContent) {

		return summaryRepository.save(
			Summary.builder()
				.writer(loginUser)
				.uploadContent(uploadContent)
				.build());
	}

	@Override
	@Transactional(readOnly = false)
	public Summary save(Summary summary) {

		return summaryRepository.save(summary);
	}

	@Override
	public List<SummaryOverviewResponse> selectSummaryListByUser(User user) {

		return summaryRepository.findAllByWriter(user).stream()
			.map(SummaryOverviewResponse::new)
			.toList();
	}

	@Override
	public String getTextByCrawlingUrl(String url) {

		return crawlingServerConnectUtil.getBodyTextByUrl(url);
	}
}

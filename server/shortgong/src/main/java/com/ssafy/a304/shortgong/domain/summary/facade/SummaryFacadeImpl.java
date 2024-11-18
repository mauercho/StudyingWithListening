package com.ssafy.a304.shortgong.domain.summary.facade;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a304.shortgong.domain.alert.repository.SseEmitters;
import com.ssafy.a304.shortgong.domain.sentence.service.SentenceService;
import com.ssafy.a304.shortgong.domain.summary.model.dto.response.SummaryDetailResponse;
import com.ssafy.a304.shortgong.domain.summary.model.dto.response.SummaryOverviewResponse;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.summary.service.SummaryService;
import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.uploadContent.service.UploadContentService;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;
import com.ssafy.a304.shortgong.domain.user.service.UserService;
import com.ssafy.a304.shortgong.global.util.S3FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 요약본과 관련된 기능 담당 Facade
 * */
@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryFacadeImpl implements SummaryFacade {

	private final UploadContentService uploadContentService;
	private final SentenceService sentenceService;
	private final SummaryService summaryService;
	private final UserService userService;
	private final SseEmitters sseEmitters;

	@Override
	public long uploadContentByFile(MultipartFile contentFile) {

		User loginUser = userService.selectLoginUser();
		// S3에 파일 업로드 -> OCR 을 통해 텍스트 추출 -> DB에 업로드컨텐츠 저장 -> 요약집 저장 (업로드컨텐츠, 업로더, 제목)
		String savedFilename = uploadContentService.uploadContentFile(contentFile);
		String text = sentenceService.getTextByFileUrlWithOcr(S3FileUtil.getPreSignedUrl(savedFilename));
		UploadContent uploadContent = uploadContentService.saveUploadContent(loginUser, text, savedFilename);
		Summary summary = summaryService.createNewSummary(loginUser, uploadContent);
		updateTitleBySummaryId(contentFile.getOriginalFilename(), summary.getId());

		// 요약집에 들어갈 문장(T, P, Q) 파싱 & 저장 & Answer (NA, SA, DA) 들만 따로 text 요청 & 저장
		// 요약집에 들어갈 문장 비동기로 파싱
		CompletableFuture<Void> parseTask = sentenceService.parseQuizSentenceList(text, summary);
		parseTask.thenRun(() -> {
			log.info("Sentence parsing completed for summaryId: {}", summary.getId());
			sseEmitters.sendAllAnswersCreatedMessageToEmitter(summary.getId());
		});
		return summary.getId();
	}

	@Override
	public long uploadTextFileByUrl(String url) {

		String text = summaryService.getTextByCrawlingUrl(url);
		MultipartFile contentFile = S3FileUtil.getTextFileByText(text);

		User loginUser = userService.selectLoginUser();
		// url -> 텍스트 크롤링 -> txt 파일 -> S3에 업로드 -> DB에 업로드컨텐츠 저장 -> 요약집 저장 (업로드컨텐츠, 업로더, 제목)
		String savedFilename = uploadContentService.uploadContentFile(contentFile);
		UploadContent uploadContent = uploadContentService.saveUploadContent(loginUser, text, savedFilename);
		Summary summary = summaryService.createNewSummary(loginUser, uploadContent);
		updateTitleBySummaryId(contentFile.getOriginalFilename(), summary.getId());

		// 요약집에 들어갈 문장(T, P, Q) 파싱 & 저장 & Answer (NA, SA, DA) 들만 따로 text 요청 & 저장
		// 요약집에 들어갈 문장 비동기로 파싱
		sentenceService.parseQuizSentenceList(text, summary);
		return summary.getId();
	}

	@Override
	public long uploadByKeyword(String keyword) {

		User loginUser = userService.selectLoginUser();
		// url -> 텍스트 크롤링 -> txt 파일 -> S3에 업로드 -> DB에 업로드컨텐츠 저장 -> 요약집 저장 (업로드컨텐츠, 업로더, 제목)
		String text = summaryService.getTextByKeyword(keyword);
		MultipartFile contentFile = S3FileUtil.getTextFileByText(text);
		String savedFilename = uploadContentService.uploadContentFile(contentFile);
		UploadContent uploadContent = uploadContentService.saveUploadContent(loginUser, text, savedFilename);
		Summary summary = summaryService.createNewSummary(loginUser, uploadContent);
		updateTitleBySummaryId(contentFile.getOriginalFilename(), summary.getId());

		// 요약집에 들어갈 문장(T, P, Q) 파싱 & 저장 & Answer (NA, SA, DA) 들만 따로 text 요청 & 저장
		// 요약집에 들어갈 문장 비동기로 파싱
		sentenceService.parseQuizSentenceListByKeyword(text, summary);
		return summary.getId();
	}

	/**
	 * 요약집의 상세 페이지에 필요한 정보
	 *
	 * @return SummaryDetailResponse(요약집 제목, 문장들의 정보)
	 * @author 정재영
	 */
	@Override
	public SummaryDetailResponse getSummaryDetail(Long summaryId) {

		return SummaryDetailResponse.builder()
			.summaryTitle(summaryService.selectSummaryById(summaryId).getTitle())
			.sentenceResponseList(sentenceService.searchAllSentenceResponseBySummaryId(summaryId))
			.build();
	}

	@Override
	public void updateTitleBySummaryId(String title, Long summaryId) {

		Summary summary = summaryService.selectSummaryById(summaryId);
		summary.updateTitle(title);
		summaryService.save(summary);
	}

	@Override
	public List<SummaryOverviewResponse> getSummaryList() {

		User loginUser = userService.selectLoginUser();
		return summaryService.searchSummaryListByUser(loginUser);
	}
}

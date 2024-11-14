package com.ssafy.a304.shortgong.domain.summary.facade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
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

	@Override
	public long uploadContentByFile(MultipartFile contentFile) {

		User loginUser = userService.selectLoginUser();
		// S3에 파일 업로드 -> OCR 을 통해 텍스트 추출 -> DB에 업로드컨텐츠 저장 -> 요약집 저장 (업로드컨텐츠, 업로더, 제목)
		String savedFilename = uploadContentService.uploadContentFile(contentFile);
		String text = sentenceService.getTextByFileUrlWithOcr(S3FileUtil.getPreSignedUrl(savedFilename));
		UploadContent uploadContent = uploadContentService.saveUploadContent(loginUser, text, savedFilename);
		Summary summary = summaryService.createNewSummary(loginUser, uploadContent);
		updateTitleBySummaryId(contentFile.getOriginalFilename(), summary.getId());

		// 요약집에 들어갈 문장(T, P, Q) 파싱 & 저장
		List<Sentence> sentenceList = sentenceService.parseQuizSentenceList(text, summary);
		sentenceList = sentenceService.saveSentences(sentenceList);

		// Answer 들만 따로 요청 & 일괄 저장
		List<Sentence> sentenceListContainAnswers = sentenceList.stream().map(
			// sentence 에 빈 부분 (NA, SA, DA) 채우기
			sentence -> sentenceService.setAnswers(sentence, text)).toList();
		sentenceListContainAnswers = sentenceService.saveSentences(sentenceListContainAnswers);

		// 문장들을 TTS 요청하여 S3에 업로드하기
		sentenceListContainAnswers.forEach(sentenceService::uploadSentenceVoice);
		return summary.getId();
	}

	@Override
	public long uploadTextFileByUrl(String url) {

		User loginUser = userService.selectLoginUser();
		// url -> 텍스트 크롤링 -> txt 파일 -> S3에 업로드 -> DB에 업로드컨텐츠 저장 -> 요약집 저장 (업로드컨텐츠, 업로더, 제목)
		String text = summaryService.getTextByCrawlingUrl(url);
		MultipartFile contentFile = S3FileUtil.getTextFileByText(text);
		String savedFilename = uploadContentService.uploadContentFile(contentFile);
		UploadContent uploadContent = uploadContentService.saveUploadContent(loginUser, text, savedFilename);
		Summary summary = summaryService.createNewSummary(loginUser, uploadContent);
		updateTitleBySummaryId(contentFile.getOriginalFilename(), summary.getId());

		// 요약집에 들어갈 문장(T, P, Q) 파싱 & 저장
		List<Sentence> sentenceList = sentenceService.parseQuizSentenceList(text, summary);
		sentenceList = sentenceService.saveSentences(sentenceList);

		// Answer 들만 따로 요청 & 일괄 저장
		List<Sentence> sentenceListContainAnswers = sentenceList.stream().map(
			// sentence 에 빈 부분 (NA, SA, DA) 채우기
			sentence -> sentenceService.setAnswers(sentence, text)).toList();
		sentenceListContainAnswers = sentenceService.saveSentences(sentenceListContainAnswers);

		// 문장들을 TTS 요청하여 S3에 업로드하기
		sentenceListContainAnswers.forEach(sentenceService::uploadSentenceVoice);
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

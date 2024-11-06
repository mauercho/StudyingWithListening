package com.ssafy.a304.shortgong.domain.summary.facade;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.service.SentenceService;
import com.ssafy.a304.shortgong.domain.summary.model.dto.response.SummaryDetailResponse;
import com.ssafy.a304.shortgong.domain.summary.model.entity.Summary;
import com.ssafy.a304.shortgong.domain.summary.service.SummaryService;
import com.ssafy.a304.shortgong.domain.uploadContent.model.entity.UploadContent;
import com.ssafy.a304.shortgong.domain.uploadContent.service.UploadContentService;
import com.ssafy.a304.shortgong.domain.user.model.entity.User;
import com.ssafy.a304.shortgong.domain.user.service.UserService;
import com.ssafy.a304.shortgong.global.model.entity.ClaudeResponseMessage;
import com.ssafy.a304.shortgong.global.util.FileUtil;
import com.ssafy.a304.shortgong.global.util.SentenceUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 요약본과 관련된 기능 담당 Facade
 * */
@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryFacadeImpl implements SummaryFacade {

	private final SummaryService summaryService;
	private final UploadContentService uploadContentService;
	private final UserService userService;
	private final SentenceService sentenceService;
	private final SentenceUtil sentenceUtil;

	@Override
	@Transactional
	public long uploadContent(MultipartFile contentFile) {
		// 로그인 유저 가져오기
		User loginUser = userService.selectLoginUser();

		// 업로드 컨텐츠 파일 S3에 업로드
		String savedFilename = uploadContentService.uploadContentFile(contentFile);
		String uploadContentUrl = FileUtil.getUploadContentUrl(savedFilename);

		// TODO : txt 파일이면 그냥 text 만 추출하기
		// if ("savedFilename 의 확장자" == "txt") {
		// 	String text = FileUtil.getTextByTxtFile(contentFile);
		// }

		String text = sentenceService.getTextByImgFileNameWithOcr(uploadContentUrl);

		// 업로드 컨텐츠 db에 저장
		UploadContent uploadContent = uploadContentService.saveUploadContent(loginUser, text, savedFilename);

		// 요약집 저장하기
		Summary summary = summaryService.createNewSummary(loginUser, uploadContent);

		// TODO : 요약집 제목 수정 (자동 기입) 하기
		// summaryService.updateTitle(summary);

		// TODO : 목차 생성

		// text 를 요약해서 summarizedText 만들고 문장으로 split
		AtomicInteger orderCounter = new AtomicInteger(1);

		List<ClaudeResponseMessage> claudeResponseMessageList = sentenceService.getSummarizedText(text);
		List<Sentence> sentenceList = claudeResponseMessageList.stream()
			.flatMap(claudeResponseMessage -> {
				String summarizedText = claudeResponseMessage.getText();
				List<String> summarizedSentenceList = sentenceUtil.splitByNewline(summarizedText);

				return summarizedSentenceList.stream()
					.map(summarizedSentence ->
						Sentence.builder()
							.sentenceContent(summarizedSentence)
							.order(orderCounter.getAndIncrement())
							.summary(summary)
							.openStatus(true)
							.build());
			})
			.toList();
		log.debug("문장 리스트: {}", sentenceList);

		//  문장들 db에 저장
		sentenceService.saveSentences(sentenceList);

		// 문장들을 TTS 요청하여 S3에 업로드하기
		sentenceList.forEach(sentenceService::uploadSentenceVoice);

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
}

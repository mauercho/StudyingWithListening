package com.ssafy.a304.shortgong.domain.summary.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.ssafy.a304.shortgong.global.util.ClovaVoiceUtil;
import com.ssafy.a304.shortgong.global.util.FileUtil;

import lombok.RequiredArgsConstructor;

/**
 * 요약본과 관련된 기능 담당 Facade
 * */
@Service
@RequiredArgsConstructor
public class SummaryFacadeImpl implements SummaryFacade {

	private final SummaryService summaryService;
	private final UploadContentService uploadContentService;
	private final UserService userService;
	private final SentenceService sentenceService;
	private final ClovaVoiceUtil clovaVoiceUtil;

	@Override
	@Transactional
	public long uploadContent(MultipartFile contentFile) {
		// 로그인 유저 가져오기
		User loginUser = userService.selectLoginUser();

		// 업로드 컨텐츠 파일 S3에 저장
		String savedFilename = FileUtil.uploadContentFileByUuid(contentFile, UUID.randomUUID().toString());

		// TODO : txt 파일이면 그냥 text 만 추출하기
		// if ("savedFilename 의 확장자" == "txt") {
		// 	String text = FileUtil.getTextByTxtFile(contentFile);
		// }

		// TODO : Ocr (Image -> Text)
		String text = "ClovaOcrUtil.getTextByImage(contentFile)";

		// 업로드 컨텐츠 db에 저장
		UploadContent uploadContent = uploadContentService.saveUploadContent(
			UploadContent.builder()
				.user(loginUser)
				.content(text)
				.fileName(savedFilename)
				.build());

		// 요약집 저장하기
		Summary summary = summaryService.createNewSummary(loginUser, uploadContent);

		// TODO : 요약집 제목 수정 (자동 기입) 하기
		// summaryService.updateTitle(summary);

		// TODO : text 요약해서 summarizedText 만들기
		// String summarizedText = sentenceService.summarizeText(text);

		// TODO : 목차 생성

		// TODO : 요청 온 요약 텍스트 문장으로 split
		// List<SentenceResponse> sentenceResponseList = sentenceService.convertToList(summarizedText);

		// TODO : 문장들을 TTS 요청하여 저장하기
		// 예시
		List<Sentence> sentenceList = new ArrayList<>();
		sentenceList.add(Sentence.builder()
			.order(1)
			.summary(summary)
			.sentenceContent("test sentence 1.")
			.build());
		sentenceList.add(Sentence.builder()
			.order(2)
			.summary(summary)
			.sentenceContent("test sentence 2.")
			.build());
		sentenceList.add(Sentence.builder()
			.order(3)
			.summary(summary)
			.sentenceContent("test sentence 3.")
			.build());

		sentenceList.forEach(sentence -> sentenceService.saveSentences(sentenceList));

		sentenceList.forEach(sentenceService::addSentenceVoice);

		// TODO : 문장들 db에 저장
		// sentenceService.saveSentences(sentenceResponseList);
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
}
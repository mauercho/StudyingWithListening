package com.ssafy.a304.shortgong.domain.sentence.service;

import static com.ssafy.a304.shortgong.global.model.constant.ClovaVoice.*;
import static com.ssafy.a304.shortgong.global.model.constant.ElevenLabsVoice.*;

import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;
import com.ssafy.a304.shortgong.global.util.ClovaVoiceUtil;
import com.ssafy.a304.shortgong.global.util.ElevenLabsVoiceUtil;
import com.ssafy.a304.shortgong.global.util.RandomUtil;
import com.ssafy.a304.shortgong.global.util.S3FileUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoiceAsyncServiceImpl implements VoiceAsyncService {

	private final SentenceRepository sentenceRepository;
	private final ElevenLabsVoiceUtil elevenLabsVoiceUtil;
	private final ClovaVoiceUtil clovaVoiceUtil;

	/**
	 * 문장에 해당하는 voice 생성 & 저장
	 * */
	@Async
	@Override
	public void uploadSentenceVoice(Sentence sentence) throws TaskRejectedException {

		String summaryFolderName = sentence.getSummary().getFolderName();

		if (sentence.getQuestion() != null) {
			byte[] questionVoiceData = clovaVoiceUtil.requestVoiceByTextAndVoice(
				sentence.getQuestion(),
				DONGHYUN.getName());
			String questionFileName = S3FileUtil.uploadSentenceVoiceFileByUuid(
				questionVoiceData,
				summaryFolderName,
				RandomUtil.generateUUID());
			sentence.updateQuestionVoiceFileName(questionFileName);
		}
		if (sentence.getSentenceContentNormal() != null) {
			byte[] normalVoiceData = elevenLabsVoiceUtil.requestVoiceByTextAndVoice(
				sentence.getSentenceContentNormal(),
				ALICE.getVoiceId());
			String normalFileName = S3FileUtil.uploadSentenceVoiceFileByUuid(
				normalVoiceData,
				summaryFolderName,
				RandomUtil.generateUUID());
			sentence.updateNormalVoiceFileName(normalFileName);
		}
		if (sentence.getSentenceContentSimple() != null) {
			byte[] simpleVoiceData = elevenLabsVoiceUtil.requestVoiceByTextAndVoice(
				sentence.getSentenceContentSimple(),
				ALICE.getVoiceId());
			String simpleFileName = S3FileUtil.uploadSentenceVoiceFileByUuid(
				simpleVoiceData,
				summaryFolderName,
				RandomUtil.generateUUID());
			sentence.updateSimpleVoiceFileName(simpleFileName);
		}
		if (sentence.getSentenceContentDetail() != null) {
			byte[] detailVoiceData = elevenLabsVoiceUtil.requestVoiceByTextAndVoice(
				sentence.getSentenceContentDetail(),
				ALICE.getVoiceId());
			String detailFileName = S3FileUtil.uploadSentenceVoiceFileByUuid(
				detailVoiceData,
				summaryFolderName,
				RandomUtil.generateUUID());
			sentence.updateDetailVoiceFileName(detailFileName);
		}
		sentenceRepository.save(sentence);
	}
}

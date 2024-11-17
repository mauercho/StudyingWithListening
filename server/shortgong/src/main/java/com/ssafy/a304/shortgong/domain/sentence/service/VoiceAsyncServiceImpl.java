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
			elevenLabsVoiceUtil.requestVoiceByTextAndVoiceAsync(
				sentence.getSentenceContentNormal(),
				ALICE.getVoiceId(),
				new ElevenLabsVoiceUtil.Callback() {
					@Override
					public void onSuccess(byte[] voice) {

						String normalFileName = S3FileUtil.uploadSentenceVoiceFileByUuid(
							voice,
							summaryFolderName,
							RandomUtil.generateUUID());
						sentence.updateNormalVoiceFileName(normalFileName);
						sentenceRepository.save(sentence);
					}

					@Override
					public void onError(Exception e) {

						System.err.println("Error: " + e.getMessage());
					}
				});

		}
		if (sentence.getSentenceContentSimple() != null) {
			elevenLabsVoiceUtil.requestVoiceByTextAndVoiceAsync(
				sentence.getSentenceContentSimple(),
				ALICE.getVoiceId(),
				new ElevenLabsVoiceUtil.Callback() {
					@Override
					public void onSuccess(byte[] voice) {

						String simpleFileName = S3FileUtil.uploadSentenceVoiceFileByUuid(
							voice,
							summaryFolderName,
							RandomUtil.generateUUID());
						sentence.updateSimpleVoiceFileName(simpleFileName);
						sentenceRepository.save(sentence);
					}

					@Override
					public void onError(Exception e) {

						System.err.println("Error: " + e.getMessage());
					}
				});

		}
		if (sentence.getSentenceContentDetail() != null) {
			elevenLabsVoiceUtil.requestVoiceByTextAndVoiceAsync(
				sentence.getSentenceContentDetail(),
				ALICE.getVoiceId(),
				new ElevenLabsVoiceUtil.Callback() {
					@Override
					public void onSuccess(byte[] voice) {

						String detailFileName = S3FileUtil.uploadSentenceVoiceFileByUuid(
							voice,
							summaryFolderName,
							RandomUtil.generateUUID());
						sentence.updateDetailVoiceFileName(detailFileName);
						sentenceRepository.save(sentence);
					}

					@Override
					public void onError(Exception e) {

						System.err.println("Error: " + e.getMessage());
					}
				});
		}
	}
}

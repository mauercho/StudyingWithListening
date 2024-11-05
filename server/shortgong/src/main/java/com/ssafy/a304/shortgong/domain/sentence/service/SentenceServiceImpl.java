package com.ssafy.a304.shortgong.domain.sentence.service;

import static com.ssafy.a304.shortgong.global.errorCode.SentenceErrorCode.*;
import static com.ssafy.a304.shortgong.global.model.constant.ClovaVoice.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentenceResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.dto.response.SentencesCreateResponse;
import com.ssafy.a304.shortgong.domain.sentence.model.entity.Sentence;
import com.ssafy.a304.shortgong.domain.sentence.repository.SentenceRepository;
import com.ssafy.a304.shortgong.global.error.CustomException;
import com.ssafy.a304.shortgong.global.util.ClovaVoiceUtil;
import com.ssafy.a304.shortgong.global.util.FileUtil;
import com.ssafy.a304.shortgong.global.util.RandomUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SentenceServiceImpl implements SentenceService {

	private final SentenceRepository sentenceRepository;
	private final ClovaVoiceUtil clovaVoiceUtil;

	/**
	 * 문장에 해당하는 voice 생성 & 저장
	 * @return 파일명
	 * */
	@Override
	public void addSentenceVoice(Sentence sentence) {

		byte[] voiceData = clovaVoiceUtil.requestVoiceByTextAndVoice(sentence.getSentenceContent(),
			DSINU_MATT.getName());

		// TODO : 이미 파일이 존재하면 삭제하기

		String fileName = FileUtil.uploadSentenceVoiceFileByUuid(
			voiceData,
			sentence.getSummary().getFolderName(),
			RandomUtil.generateUUID());

		sentence.updateVoiceFileName(fileName);
	}

	@Override
	public Sentence selectSentenceById(Long sentenceId) throws CustomException {

		return sentenceRepository.findById(sentenceId)
			.orElseThrow(() -> new CustomException(SENTENCE_FIND_FAIL));
	}

	@Override
	public List<Sentence> selectAllSentenceBySummaryId(Long summaryId) {

		return sentenceRepository.findAllBySummary_IdOrderByOrder(summaryId);
	}

	@Override
	@Transactional
	public List<Sentence> saveSentences(List<Sentence> sentences) {

		return sentenceRepository.saveAll(sentences);
	}

	/**
	 * 요약본 상세 페이지에 필요한 문장 리스트 가져오기
	 * @author 정재영
	 */
	@Override
	public List<SentenceResponse> searchAllSentenceResponseBySummaryId(Long summaryId) {

		return selectAllSentenceBySummaryId(summaryId).stream()
			.map(sentence -> SentenceResponse.builder()
				.sentence(sentence)
				.build())
			.collect(Collectors.toList());
	}

	@Override
	public String convertSentenceListToString(List<Sentence> sentenceList) {

		StringBuilder sb = new StringBuilder();
		for (Sentence sentence : sentenceList) {
			sb.append(sentence.getSentenceContent());
			sb.append(" ");
		}
		return sb.toString();
	}

	@Override
	@Transactional
	public SentencesCreateResponse getModifySentences(Sentence existingSentence, String claudeResponse) {

		List<String> newSentences = splitToSentences(claudeResponse);
		Long summaryId = existingSentence.getSummary().getId();
		int existingOrder = existingSentence.getOrder();

		// 벌크 연산으로 기존 문장 다음 order들을 newSenteces의 size만큼 증가시킴
		int increment = newSentences.size() - 1;
		if (increment > 0)
			sentenceRepository.bulkUpdateOrder(summaryId, existingOrder, increment);

		// 문장 업데이트
		existingSentence.setSentenceContent(newSentences.get(0));
		existingSentence.updateVoiceFileName(
			createNewSentenceVoice(newSentences.get(0), existingSentence.getSummary().getFolderName()));
		List<Sentence> newSentenceEntities = new ArrayList<>(List.of(existingSentence));
		for (int i = 1; i < newSentences.size(); i++) {
			Sentence newSentence = Sentence.builder()
				.summary(existingSentence.getSummary())
				.sentenceContent(newSentences.get(i))
				.order(existingOrder + i)
				.voiceFileName(
					createNewSentenceVoice(newSentences.get(i), existingSentence.getSummary().getFolderName()))
				.build();
			addSentenceVoice(newSentence);
			newSentenceEntities.add(newSentence);
		}
		return SentencesCreateResponse.of(saveSentences(newSentenceEntities));
	}

	/**
	 * String을 받아서 문장 부호 기준으로 나누어 List로 반환
	 * @return List<String>: 문장 리스트
	 * @author 이주형
	 */
	private List<String> splitToSentences(String text) {

		if (text == null || text.isEmpty()) {
			return List.of("");
		}

		List<String> sentences = new ArrayList<>();
		// 정규식을 사용하여 문장과 문장 부호를 함께 캡처
		Pattern pattern = Pattern.compile("[^.!?]+[.!?]");
		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			sentences.add(matcher.group());
		}

		return sentences;
	}

	@Override
	public String getRecreatePrompt(String sentencesString, String sentenceContent) {

		return "당신은 자연어 처리의 전문가로서, 문맥의 일관성을 유지하면서 문장을 개선하는 데 특화되어 있습니다. "
			+ "주어진 텍스트에서 특정 문장을 재생성하는 것이 당신의 임무입니다. "
			+ "이때, 문장이 주변 텍스트와 문맥적으로 일관성을 유지하면서도 명확성, 흐름, 혹은 표현력을 필요에 따라 향상시키도록 합니다. "
			+ "재생성된 문장은 다음의 요구사항을 충족해야 합니다:\n"
			+ "1. 원래의 의도와 의미를 유지할 것.\n"
			+ "2. 원래 문맥에 매끄럽게 맞아떨어질 것.\n"
			+ "3. 전체 메시지를 변경하지 않으면서 자연스러움, 명확성, 혹은 스타일을 향상시킬 수 있음.\n"
			+ "4. 필요할 경우, 어휘 선택이나 문장 구조를 개선하여 보다 매력적이고 설득력 있는 문장으로 바꿀 것.\n"
			+ "5. 문장의 흐름과 일관성을 유지하며, 독자가 쉽게 이해할 수 있도록 명확성을 높일 것.\n"
			+ "6. 문장을 개선할 때, 사용자의 감정을 고려하여 긍정적이고 부드러운 어조를 유지하도록 할 것.\n"
			+ "7. 문맥에 따라 전문 용어나 비유 등을 추가하여 독자가 주제를 더 깊이 이해할 수 있도록 할 것.\n"
			+ "8. 문장의 리듬과 운율을 고려하여 읽기 쉽고 자연스럽게 들리도록 조정할 것.\n"
			+ "9. 동일한 의미를 전달하는 여러 표현을 제시하여 다양한 접근 방식을 제공할 것.\n\n"
			// + "10. 대상 독자의 이해 수준을 고려하여 문장을 단순화하거나 복잡하게 조정할 수 있음.\n" -> 독자 이해 수준 변경할 때 추가될 수도
			+ "지침:\n"
			+ "- 입력: 본문 텍스트와 재생성해야 할 특정 대상 문장.\n"
			+ "- 출력: 주어진 문맥에 완벽히 부합하는 형태로, 위에서 언급한 요구사항을 충족한, 재구성된 대상 문장.\n"
			+ "- 문장의 자연스러움과 맥락적 일관성을 고려하여, "
			+ "단순히 문장을 바꾸는 것에 그치지 않고 전체적인 문장의 흐름을 더욱 부드럽고 읽기 쉽게 만들도록 노력합니다.\n"
			+ "- 재생성 시 다양한 표현 방식을 고려하여, 독자가 문장을 다각도로 이해할 수 있도록 지원합니다.\n\n"

			+ "예시:\n"
			+ "입력 문단: \"프로젝트에는 많은 도전 과제가 있었고, 특히 촉박한 일정이 문제였습니다. "
			+ "하지만 팀은 큰 인내심을 발휘하여 제시간에 작업을 완료했습니다. "
			+ "그들의 노력은 칭찬할 만했고, 그들의 노고는 결실을 맺었습니다.\"\n"
			+ "대상 문장: \"하지만 팀은 큰 인내심을 발휘하여 제시간에 작업을 완료했습니다.\"\n"
			+ "재생성된 문장: \"촉박한 일정에도 불구하고, 팀은 놀라운 인내심을 보여주며 작업을 성공적으로 제시간에 마쳤습니다.\"\n\n"

			+ "또 다른 예시:\n"
			+ "입력 문단: \"새로운 시스템의 도입으로 인해 초기에는 많은 혼란이 있었지만, 직원들은 빠르게 적응해 나갔습니다. "
			+ "그 결과, 업무 효율성이 눈에 띄게 향상되었습니다.\"\n"
			+ "대상 문장: \"직원들은 빠르게 적응해 나갔습니다.\"\n"
			+ "재생성된 문장: \"직원들은 새로운 시스템에 신속하게 적응하여 혼란을 최소화했습니다.\"\n\n"

			+ "이제 주어진 문맥을 바탕으로 대상 문장을 재생성해 주세요. "
			+ "이때, 원래 문맥과의 일관성을 유지하면서도 문장의 명확성, 흐름, 그리고 표현력 등을 개선하여 더욱 완성도 높은 문장으로 만들어 주세요. "
			+ "다양한 표현 방식과 독자 맞춤형 접근 방식을 고려하여 최상의 결과를 도출해 주세요.\n\n"

			+ "입력 문단은 다음과 같습니다. \n\n"
			+ "--------------------------------\n"
			+ sentencesString
			+ "\n--------------------------------\n\n"
			+ "대상 문장은 다음과 같습니다. \n\n"
			+ "--------------------------------\n"
			+ sentenceContent
			+ "\n--------------------------------\n\n";
	}

	@Override
	public String getDetailPrompt(String sentencesString, String sentenceContent) {

		return "나는 너에게 긴 텍스트 하나를 건네 줄 거야. 그 긴 텍스트는 다음과 같아. \n"
			+ sentencesString
			+ "\n\n위 텍스트의 전체 맥락을 고려하여, 내가 다음으로 전해주는 문장을 더 친절하고 자세하고 구체적으로 제공해줘\n---\n"
			+ sentenceContent
			+ "\n---\n'전체 텍스트의 맥락을 고려했을 때, 해당 문장을 다음과 같이 수정하면 좋을 것 같습니다:' 같은 멘트나 "
			+ "'수정 이유는 다음과 같습니다:'와 같은 멘트는 필요 없어. 한자가 포함되면 안 돼. 일본어나 중국어와 같은 외국어도 "
			+ "포함되면 안 돼. "
			+ " 2문장으로 제공해줘.";
	}

	@Override
	@Transactional
	public void updateSentenceOpenStatus(Long sentenceId, Boolean openStatus) {

		Sentence sentence = selectSentenceById(sentenceId);
		sentence.updateOpenStatus(openStatus);
		sentenceRepository.save(sentence);
	}

	/**
	 * text를 받아서 clova voice로 변환하여 voice 파일을 생성하고, 파일명을 반환
	 * @return 파일명
	 * @author 이주형
	 */
	@Transactional
	public String createNewSentenceVoice(String content, String folderName) {

		byte[] voiceData = clovaVoiceUtil.requestVoiceByTextAndVoice(content, DSINU_MATT.getName());
		return FileUtil.uploadSentenceVoiceFileByUuid(voiceData, folderName, RandomUtil.generateUUID());
	}

}

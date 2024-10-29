package com.ssafy.domain.ai_talk.service;

import com.ssafy.domain.ai_talk.model.dto.Message;
import com.ssafy.domain.ai_talk.model.dto.request.AiTalkRequest;
import com.ssafy.domain.ai_talk.model.dto.request.AiTalkSituationRequest;
import com.ssafy.domain.ai_talk.model.dto.request.GPTRequest;
import com.ssafy.domain.ai_talk.model.dto.request.GPTSituationRequest;
import com.ssafy.domain.ai_talk.model.dto.response.AiTalkFinishResponse;
import com.ssafy.domain.ai_talk.model.dto.response.AiTalkResponse;
import com.ssafy.domain.ai_talk.model.dto.response.AiTalkSituationResponse;
import com.ssafy.domain.ai_talk.model.dto.response.GPTResponse;
import com.ssafy.domain.ai_talk.model.entity.AiTalkSituation;
import com.ssafy.domain.ai_talk.repository.AiTalkRepository;
import com.ssafy.domain.user.model.entity.User;
import com.ssafy.domain.user.repository.UserRepository;
import com.ssafy.domain.user.service.AiTalkLogService;
import com.ssafy.domain.user.service.StudyTimeService;
import com.ssafy.global.common.UserInfoProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiTalkServiceImpl implements AiTalkService {

    private final UserInfoProvider userInfoProvider;
    private final UserRepository userRepository;
    private final AiTalkRepository aiTalkRepository;
    private final CacheService cacheService;
    private final StudyTimeService studyTimeService;
    private final AiTalkLogService aiTalkLogService;

    @Value("${openai.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openai.com/v1/")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @Override
    public List<AiTalkSituationResponse> getAllAiTalkSituations() {
        List<AiTalkSituation> aiTalkSituations = aiTalkRepository.findAll();
        if (aiTalkSituations.isEmpty())
            return null;
        return aiTalkSituations.stream().map(AiTalkSituationResponse::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AiTalkSituationResponse> getAiTalkSituationByTopic(String topic) {
        List<AiTalkSituation> aiTalkSituations = aiTalkRepository.findByTopic(topic);
        if (aiTalkSituations.isEmpty())
            return null;
        return aiTalkSituations.stream().map(AiTalkSituationResponse::toDto).collect(Collectors.toList());
    }

    @Override
    public AiTalkResponse setSituation(Long situationId) {
        Long userId = userInfoProvider.getCurrentUserId();
        aiTalkLogService.createStartTimeLog(userId);

        String loginId = userInfoProvider.getCurrentLoginId();
        AiTalkSituationRequest aiTalkSituation = aiTalkRepository.findById(situationId)
                .map(AiTalkSituationRequest::toDto)
                .orElse(null);

        if (aiTalkSituation == null) {
            throw new NoSuchElementException("Situation with id " + situationId + " not found.");
        }

        cacheService.initCacheMemory(loginId, aiTalkSituation);
        GPTSituationRequest gptSituationRequest = new GPTSituationRequest(aiTalkSituation);
        GPTResponse gptResponse = webClient.method(HttpMethod.POST)
                .uri("chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(gptSituationRequest)
                .retrieve()
                .bodyToMono(GPTResponse.class)
                .block();

        String aiResponse = gptResponse.getChoices().get(0).getMessage().getContent();
        cacheService.addChatHistory(loginId, new Message("assistant", aiResponse));
        return new AiTalkResponse(aiTalkSituation.getUserRole(), aiTalkSituation.getAiRole(), aiResponse);
    }

    @Override
    public AiTalkResponse getAiResponse(AiTalkRequest aiTalkRequest) {
        AiTalkSituationRequest aiTalkSituation = aiTalkRepository.findById(aiTalkRequest.getSituationId())
                .map(AiTalkSituationRequest::toDto)
                .orElse(null);

        if (aiTalkSituation == null) {
            throw new NoSuchElementException("Situation with id " + aiTalkRequest.getSituationId() + " not found.");
        }

        String loginId = userInfoProvider.getCurrentLoginId();
        // 이전 대화 가져오기
        List<Message> chatHistory = cacheService.getChatHistory(loginId);
        GPTRequest gptRequest = new GPTRequest(chatHistory);
        gptRequest.addMessage(aiTalkRequest.getMessage());

        GPTResponse gptResponse = webClient.method(HttpMethod.POST)
                .uri("chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(gptRequest)
                .retrieve()
                .bodyToMono(GPTResponse.class)
                .block();

        String aiResponse = gptResponse.getChoices().get(0).getMessage().getContent();

        cacheService.addChatHistory(loginId, new Message("user", aiTalkRequest.getMessage()));
        cacheService.addChatHistory(loginId, new Message("assistant", aiResponse));


        return new AiTalkResponse(aiTalkSituation.getUserRole(), aiTalkSituation.getAiRole(), aiResponse);
    }

    @Override
    public AiTalkFinishResponse finishAiResponse() {
        // 이외에 AI 응답 끝내는 로직 추가
        User user = userInfoProvider.getCurrentUser();
        cacheService.clearChatHistory(user.getLoginId());

        aiTalkLogService.createEndTimeLog(user.getUserId());
        int leaves = calculateLeaves(studyTimeService.increaseAiTalkMinutes());

        user.increaseUserLeaves(leaves);
        userRepository.save(user);

        return AiTalkFinishResponse.toDto(leaves);

    }

    public int calculateLeaves(int aiTalkTime) {
        if (aiTalkTime >= 15)
            return 10;
        if (aiTalkTime >= 8)
            return 5;
        if (aiTalkTime >= 5)
            return 3;
        if (aiTalkTime >= 2)
            return 1;
        return 0;
    }
}

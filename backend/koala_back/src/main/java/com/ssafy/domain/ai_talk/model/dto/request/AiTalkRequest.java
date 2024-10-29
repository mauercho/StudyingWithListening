package com.ssafy.domain.ai_talk.model.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AiTalkRequest {
    @JsonProperty("situation_id")
    private Long situationId;

    private String message;
}
package com.ssafy.domain.ai_talk.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AiTalkResponse {
    @JsonProperty("user_role")
    private String userRole;

    @JsonProperty("ai_role")
    private String aiRole;

    private String message;
}
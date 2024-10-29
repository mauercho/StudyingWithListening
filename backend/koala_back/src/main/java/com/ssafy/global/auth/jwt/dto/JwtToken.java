package com.ssafy.global.auth.jwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtToken {

    @JsonProperty("grant_type")
    private String grantType; //  Bearer

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

}

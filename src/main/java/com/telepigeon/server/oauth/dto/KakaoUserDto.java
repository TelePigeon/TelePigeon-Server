package com.telepigeon.server.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUserDto(Long id, KakaoAccount kakaoAccount){
    public record KakaoAccount (@JsonProperty("profile") KakaoUserProfile kakaoUserProfile, String email){
        public record KakaoUserProfile (String nickname){

        }
    }
}
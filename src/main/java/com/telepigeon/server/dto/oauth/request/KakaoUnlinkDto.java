package com.telepigeon.server.dto.oauth.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUnlinkDto (
        String targetIdType,
        Long targetId
) {
    public static KakaoUnlinkDto of (Long userId) {
        return new KakaoUnlinkDto("user_id", userId);
    }
}

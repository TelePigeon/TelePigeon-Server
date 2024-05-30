package com.telepigeon.server.dto.auth;

public record JwtTokensDto(String accessToken, String refreshToken) {
    public static JwtTokensDto of(String accessToken, String refreshToken) {
        return new JwtTokensDto(accessToken, refreshToken);
    }
}

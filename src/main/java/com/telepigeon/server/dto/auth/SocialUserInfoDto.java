package com.telepigeon.server.dto.auth;

public record SocialUserInfoDto(
        String serialId,
        String email,
        String name
) {
    public static SocialUserInfoDto of(
            String serialId,
            String email,
            String name
    ) {
        return new SocialUserInfoDto(serialId, email, name);
    }
}

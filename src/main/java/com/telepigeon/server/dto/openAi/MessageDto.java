package com.telepigeon.server.dto.openAi;

public record MessageDto(
        String role,
        String content
) {
    public static MessageDto of(String role, String content){
        return new MessageDto(role, content);
    }
}

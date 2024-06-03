package com.telepigeon.server.dto.naverCloud;

public record ConfidenceCreateDto(
        String content
) {
    public static ConfidenceCreateDto of(String content) {
        return new ConfidenceCreateDto(content);
    }
}

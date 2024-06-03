package com.telepigeon.server.dto.naverCloud.request;

public record ConfidenceCreateDto(
        String content
) {
    public static ConfidenceCreateDto of(String content) {
        return new ConfidenceCreateDto(content);
    }
}

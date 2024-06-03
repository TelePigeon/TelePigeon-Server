package com.telepigeon.server.dto.naverCloud;

public record DocumentDto(
        String sentiment,
        ConfidenceDto confidence
) {
}

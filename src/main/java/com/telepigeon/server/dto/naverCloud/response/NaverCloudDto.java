package com.telepigeon.server.dto.naverCloud.response;

import com.telepigeon.server.dto.naverCloud.ConfidenceDto;

public record NaverCloudDto(
        DocumentDto document
) {
    public record DocumentDto(
            String sentiment,
            ConfidenceDto confidence
    ) {
    }
}

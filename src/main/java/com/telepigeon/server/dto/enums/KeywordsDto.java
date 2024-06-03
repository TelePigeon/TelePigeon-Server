package com.telepigeon.server.dto.enums;

import java.util.List;

public record KeywordsDto(
        List<String> keywords
) {
    public static KeywordsDto of(List<String> keywords) {
        return new KeywordsDto(keywords);
    }
}

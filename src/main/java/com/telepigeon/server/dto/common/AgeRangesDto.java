package com.telepigeon.server.dto.common;

import java.util.List;

public record AgeRangesDto(
        List<String> ageRanges
) {
    public static AgeRangesDto of(List<String> ageRanges) {
        return new AgeRangesDto(ageRanges);
    }
}

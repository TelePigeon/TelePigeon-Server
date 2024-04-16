package com.telepigeon.server.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AgeRange {
    TEN("10대"),
    TWENTY("20대"),
    THIRTY("30대"),
    FORTY("40대"),
    FIFTY("50대"),
    SIXTY("60대"),
    SEVENTY("70대 이상");
    private final String content;
}

package com.telepigeon.server.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sex {
    Male("남성"),
    FeMale("여성");
    private final String content;
}

package com.telepigeon.server.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Keyword {
    WORKOUT("운동"),
    WALK("산책"),
    CLASS("수업"),
    NUTRIENT("영양제"),
    MEDICINE("약"),
    FOOD("밥"),
    STUDY("공부"),
    WORK("일"),
    CLEAN("청소"),
    DREAM("꿈"),
    ;
    private final String content;
}

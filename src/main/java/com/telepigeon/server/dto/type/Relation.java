package com.telepigeon.server.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Relation {
    FRIEND("지인"),
    CHILD("자식"),
    MOTHER("엄마"),
    FATHER("아빠");
    private final String content;
}

package com.telepigeon.server.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Relation {
    FRIEND("친구"),
    CHILD("자식"),
    MOTHER("엄마"),
    FATHER("아빠");
    private final String content;

    public static Relation fromContent(String content) {
        for (Relation relation : Relation.values()) {
            if (relation.getContent().equals(content)) {
                return relation;
            }
        }
        throw new IllegalArgumentException("Unknown content : " + content);
    }
}

package com.telepigeon.server.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum Gender {
    MALE("남성"),
    FEMALE("여성");
    private final String content;

    public static Gender fromContent(String content) {
        for (Gender gender : Gender.values()) {
            if (gender.getContent().equals(content)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown content : " + content);
    }
}

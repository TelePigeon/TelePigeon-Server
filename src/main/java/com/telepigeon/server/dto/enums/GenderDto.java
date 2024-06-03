package com.telepigeon.server.dto.enums;

import java.util.List;

public record GenderDto(
        List<String> gender
) {
    public static GenderDto of(List<String> gender){
        return new GenderDto(gender);
    }
}

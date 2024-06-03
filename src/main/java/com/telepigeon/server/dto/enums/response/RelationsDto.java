package com.telepigeon.server.dto.enums.response;

import java.util.List;

public record RelationsDto(
        List<String> relations
) {
    public static RelationsDto of(List<String> relations) {
        return new RelationsDto(relations);
    }
}

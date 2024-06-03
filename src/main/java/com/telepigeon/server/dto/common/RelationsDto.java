package com.telepigeon.server.dto.common;

import java.util.List;

public record RelationsDto(
        List<String> relations
) {
    public static RelationsDto of(List<String> relations) {
        return new RelationsDto(relations);
    }
}

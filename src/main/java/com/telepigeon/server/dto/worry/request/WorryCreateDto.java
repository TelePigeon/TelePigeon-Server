package com.telepigeon.server.dto.worry.request;

import java.util.List;

public record WorryCreateDto (
        String name,
        String content,
        List<String> times
) {
}

package com.telepigeon.server.dto.worry.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record WorryCreateDto (
        @NotBlank
        String name,
        @NotBlank
        String content,
        @Size(min = 1)
        List<String> times
) {
}

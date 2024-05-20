package com.telepigeon.server.dto.answer.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record AnswerCreateDto(
        @NotBlank
        String content,
        @Nullable
        String image
) {
}

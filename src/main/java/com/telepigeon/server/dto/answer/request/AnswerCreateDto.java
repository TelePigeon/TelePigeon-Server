package com.telepigeon.server.dto.answer.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record AnswerCreateDto(
        @NotBlank
        String content,
        @Nullable
        MultipartFile image
) {
}

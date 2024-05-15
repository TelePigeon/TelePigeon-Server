package com.telepigeon.server.dto.post.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;

public record PostCreateDto(
        @Nullable @Size(max=8, min=2)
        String name
) {
}

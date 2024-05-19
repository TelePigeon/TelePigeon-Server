package com.telepigeon.server.dto.post.request;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Valid
public record RoomCreateDto(
        @Nullable @Size(max=8, min=2)
        String name
) {
}

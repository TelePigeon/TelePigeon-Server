package com.telepigeon.server.dto.room.request;

import jakarta.validation.constraints.NotNull;

public record RoomEnterDto(
        @NotNull
        String code
) {
}

package com.telepigeon.server.dto.room.request;

import com.telepigeon.server.domain.Room;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomCreateDto(
        @NotNull @Size(max=8, min=2)
        String name
) {
        public static RoomCreateDto of(Room room) {
                return new RoomCreateDto(String.valueOf(room.getName()));
        }
}

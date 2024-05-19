package com.telepigeon.server.dto.room.request;

import com.telepigeon.server.domain.Room;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@Valid
public record RoomCreateDto(
        @Nullable @Size(max=8, min=2)
        String name
) {
        public static RoomCreateDto of(Room room) {
                return new RoomCreateDto(String.valueOf(room.getName()));
        }
}

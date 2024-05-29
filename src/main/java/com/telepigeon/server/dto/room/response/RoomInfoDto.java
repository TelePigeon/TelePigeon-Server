package com.telepigeon.server.dto.room.response;

import com.telepigeon.server.domain.Room;

public record RoomInfoDto(
        String code,
        String name
) {
    public static RoomInfoDto of(Room room) {
        return new RoomInfoDto(
                room.getCode(),
                room.getName()
        );
    }
}

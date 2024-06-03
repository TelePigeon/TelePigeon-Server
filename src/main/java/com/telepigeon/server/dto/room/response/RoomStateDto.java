package com.telepigeon.server.dto.room.response;

public record RoomStateDto(
        String name,
        Integer number,
        Long days
) {
    public static RoomStateDto of(String name, Integer number, Long days){
        return new RoomStateDto(name, number, days);
    }
}

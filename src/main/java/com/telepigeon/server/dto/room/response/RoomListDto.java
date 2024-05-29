package com.telepigeon.server.dto.room.response;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;

import java.util.List;

public record RoomListDto(
        List<RoomDto> rooms
) {

    public static RoomListDto of(List<RoomDto> rooms) {
        return new RoomListDto(rooms);
    }

    public record RoomDto(
            Long roomId,
            String name,
            String opponentNickname,
            String myRelation,
            String opponentRelation,
            Integer emotion,
            Integer sentence
    ) {
        public static RoomDto of(
                Long roomId,
                String name,
                String opponentNickname,
                String myRelation,
                String opponentRelation,
                Integer emotion,
                Integer sentence
        ) {
            return new RoomDto(roomId, name, opponentNickname, myRelation,opponentRelation, emotion, sentence);
        }
    }
}

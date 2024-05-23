package com.telepigeon.server.dto.room.response;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;

public record RoomListDto(
        Long roomId,
        String name,
        String opponentNickname,
        String myRelation,
        String opponentRelation,
        Integer emotion,
        Integer sentence
) {
    public static RoomListDto of(Room room, Profile myProfile, Profile opponentProfile, Answer myAnswer, Answer opponentAnswer) {
        boolean myState = myAnswer.getContent() != null;
        boolean opponentState = opponentAnswer.getContent() != null;

        // 감정 측정 시 업데이트
        int emotion = 0;

        int sentence;
        if (myState && opponentState) {
            sentence = 0;
        } else if (myState) {
            sentence = 1;
        } else {
            sentence = 2;
        }

        return new RoomListDto(
                room.getId(),
                room.getName(),
                opponentProfile.getUser().getName(),
                myProfile.getRelation().getContent(),
                opponentProfile.getRelation().getContent(),
                emotion,
                sentence
        );
    }
}

package com.telepigeon.server.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FcmContent {
    QUESTION("질문이 도착했습니다.", "오늘의 질문을 확인하세요!", "ROOM_CLICK", "question"),
    ANSWER("답변이 도착했습니다.", "상대방의 답변을 확인하세요!", "ROOM_CLICK", "answer"),
    HURRY("재촉하기가 도착했습니다.", "상대방에게 답변을 보내보세요!", "ROOM_CLICK", "hurry"),
    EMOTION("상대방에게 먼저 연락을 해보세요.", "상대의 기분이 좋지 않은 것 같아요!", "ROOM_CLICK", "emotion"),
    ROOM_ENTER("상대방이 입장했습니다", "상대방과 대화를 시작하세요!", "ROOM_CLICK", "room"),
    ROOM_LEAVE("상대방이 퇴장했습니다", "상대방이 퇴장했습니다", "ROOM_CLICK", "room"),
    ;

    private final String title;
    private final String body;
    private final String clickAction;
    private final String type;
}

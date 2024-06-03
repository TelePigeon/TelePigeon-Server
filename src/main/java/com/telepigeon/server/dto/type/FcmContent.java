package com.telepigeon.server.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FcmContent {
    QUESTION("질문이 도착했습니다.", "오늘의 질문을 확인하세요!", "ROOM_CLICK", "question"),
    ANSWER("답변이 도착했습니다.", "상대방의 답변을 확인하세요!", "ROOM_CLICK", "answer"),
    HURRY("재촉하기가 도착했습니다.", "상대방에게 답변을 보내보세요!", "ROOM_CLICK", "hurry"),
    ;

    private final String title;
    private final String body;
    private final String clickAction;
    private final String type;
}

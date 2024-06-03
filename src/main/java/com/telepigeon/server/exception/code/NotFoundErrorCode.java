package com.telepigeon.server.exception.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NotFoundErrorCode implements DefaultErrorCode{
    NOT_FOUND_END_POINT(HttpStatus.NOT_FOUND, "error", "존재하지 않는 API입니다."),
    NOT_FOUND_HURRY(HttpStatus.NOT_FOUND, "error", "존재하지 않는 재촉하기 입니다."),
    NOT_FOUND_FILE(HttpStatus.NOT_FOUND, "error", "존재하지 않는 파일입니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 방입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 사용자입니다."),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 질문 입니다."),
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 프로필입니다."),
    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 답장입니다."),
    WORRY_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 걱정입니다"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 리프레시 토큰입니다."),
    FIREBASE_JSON_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 FIREBASE JSON입니다."),
    ;

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
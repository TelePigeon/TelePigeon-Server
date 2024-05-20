package com.telepigeon.server.exception.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum NotFoundErrorCode implements DefaultErrorCode{
    NOT_FOUND_END_POINT(HttpStatus.NOT_FOUND, "error", "존재하지 않는 API입니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 방입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 사용자입니다."),
    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "error", "존재하지 않는 프로필입니다."),
    ;

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
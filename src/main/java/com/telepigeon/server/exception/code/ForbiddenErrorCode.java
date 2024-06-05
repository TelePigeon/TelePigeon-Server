package com.telepigeon.server.exception.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ForbiddenErrorCode implements DefaultErrorCode{

    FORBIDDEN(HttpStatus.FORBIDDEN, "error", "접근 권한이 없습니다."),
    ENTER_FORBIDDEN(HttpStatus.FORBIDDEN, "error", "이미 매칭이 완료된 방입니다."),
    ;

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

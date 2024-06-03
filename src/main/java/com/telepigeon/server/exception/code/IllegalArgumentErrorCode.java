package com.telepigeon.server.exception.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum IllegalArgumentErrorCode implements DefaultErrorCode {
    ILLEGAL_ARGUMENT_DATE(HttpStatus.BAD_REQUEST, "error", "date의 인자 형식이 올바르지 않습니다."),
    ;

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

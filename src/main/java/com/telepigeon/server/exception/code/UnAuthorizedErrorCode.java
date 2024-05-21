package com.telepigeon.server.exception.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UnAuthorizedErrorCode implements DefaultErrorCode{
    //external
    INVALID_KAKAO_TOKEN(HttpStatus.UNAUTHORIZED, "error", "카카오 토큰이 유효하지 않습니다."),
    //internal
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "error", "유효하지 않은 토큰입니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "error", "만료된 토큰입니다."),
    UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED, "error", "지원하지 않는 토큰입니다."),
    JWT_IS_EMPTY(HttpStatus.UNAUTHORIZED, "error", "토큰이 비어있습니다."),
    INVALID_TOKEN_TYPE(HttpStatus.UNAUTHORIZED, "error", "유효하지 않은 토큰 타입입니다."),
    ;

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

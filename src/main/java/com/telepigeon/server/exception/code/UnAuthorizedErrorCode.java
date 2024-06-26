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
    TOKEN_TYPE_ERROR(HttpStatus.UNAUTHORIZED, "error", "토큰 타입이 잘못되거나 제공되지 않았습니다."),
    TOKEN_MALFORMED_ERROR(HttpStatus.UNAUTHORIZED, "error", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED_ERROR(HttpStatus.UNAUTHORIZED, "error", "만료된 토큰입니다."),
    TOKEN_UNSUPPORTED_ERROR(HttpStatus.UNAUTHORIZED, "error", "지원하지 않는 토큰입니다."),
    TOKEN_UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "error", "알 수 없는 토큰입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "error", "인증되지 않은 사용자입니다.")

    ;

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

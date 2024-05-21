package com.telepigeon.server.exception.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UnAuthorizedErrorCode implements DefaultErrorCode{
    INVALID_KAKAO_TOKEN(HttpStatus.UNAUTHORIZED, "error", "카카오 토큰이 유효하지 않습니다."),
    ;

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}

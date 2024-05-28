package com.telepigeon.server.exception.code;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessErrorCode implements DefaultErrorCode{
    BUSINESS_TEST(HttpStatus.OK,"conflict","선착순 마감됐어요"),
    HURRY_ALREADY_EXISTS(HttpStatus.OK, "conflict", "재촉하기를 이미 보냈습니다."),
    KAKAO_SERVER_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "external", "카카오 서버 오류"),
    INVALID_KAKAO_ADMIN_KEY(HttpStatus.INTERNAL_SERVER_ERROR, "internal", "카카오 어드민 키가 유효하지 않습니다."),
    ;
    @JsonIgnore
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}

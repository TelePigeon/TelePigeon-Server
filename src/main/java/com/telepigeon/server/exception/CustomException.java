package com.telepigeon.server.exception;

import com.telepigeon.server.dto.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException{
    private final ErrorCode errorCode;

    public String getMessage() {
        return errorCode.getMessage();
    }
}

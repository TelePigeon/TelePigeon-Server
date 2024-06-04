package com.telepigeon.server.exception;

import com.telepigeon.server.exception.code.ForbiddenErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ForbiddenException extends RuntimeException {
    private final ForbiddenErrorCode errorCode;
}

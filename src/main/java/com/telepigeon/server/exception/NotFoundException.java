package com.telepigeon.server.exception;

import com.telepigeon.server.exception.code.NotFoundErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotFoundException extends RuntimeException{
    private final NotFoundErrorCode errorCode;
}

package com.telepigeon.server.exception;

import com.telepigeon.server.exception.code.BusinessErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {
    private final BusinessErrorCode errorCode;
}

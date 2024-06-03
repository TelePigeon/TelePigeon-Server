package com.telepigeon.server.exception;

import com.telepigeon.server.exception.code.IllegalArgumentErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IllegalArgumentException extends RuntimeException {
    private final IllegalArgumentErrorCode errorCode;
}

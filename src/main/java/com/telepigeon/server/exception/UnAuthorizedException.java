package com.telepigeon.server.exception;

import com.telepigeon.server.exception.code.UnAuthorizedErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UnAuthorizedException extends RuntimeException{
    private final UnAuthorizedErrorCode errorCode;
}

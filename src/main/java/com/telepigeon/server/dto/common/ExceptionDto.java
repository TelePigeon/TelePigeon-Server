package com.telepigeon.server.dto.common;

import com.telepigeon.server.dto.type.ErrorCode;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ExceptionDto (
        @NotNull
        Integer code,
        @NotNull
        String message
) {
    public static ExceptionDto of(ErrorCode errorCode) {
        return ExceptionDto.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}

package com.telepigeon.server.dto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telepigeon.server.dto.type.ErrorCode;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNullApi;

public record ApiResponse<T> (
        @JsonIgnore
        HttpStatus httpStatus,
        boolean success,
        @Nullable T data,
        @Nullable ExceptionDto error
){
    public static <T> ApiResponse<T> ok(@Nullable final T data) {
        return new ApiResponse<>(HttpStatus.OK, true, data, null);
    }

    public static <T> ApiResponse<T> created(@Nullable final T data) {
        return new ApiResponse<>(HttpStatus.CREATED, true, data, null);
    }

    public static <T> ApiResponse<T> fail(ErrorCode errorcode) {
        return new ApiResponse<>(errorcode.getHttpStatus(), false, null, ExceptionDto.of(errorcode));
    }
}

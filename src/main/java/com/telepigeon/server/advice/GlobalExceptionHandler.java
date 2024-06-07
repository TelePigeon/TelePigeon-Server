package com.telepigeon.server.advice;

import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.code.IllegalArgumentErrorCode;
import com.telepigeon.server.exception.code.InternalServerErrorCode;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 요청은 정상이나 비즈니스 중 실패가 있는 경우
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessErrorCode> handleBusinessException(BusinessException e) {
        log.error("GlobalExceptionHandler catch BusinessException : {}", e.getErrorCode().getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode());
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<NotFoundErrorCode> handleNotFoundException(NotFoundException e){
        log.error("GlobalExceptionHandler catch NotFoundException : {}", e.getErrorCode().getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode());
    }

    // 존재하지 않는 요청에 대한 예외
    @ExceptionHandler(value = {NoHandlerFoundException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<NotFoundErrorCode> handleNoPageFoundException(Exception e) {
        log.error("GlobalExceptionHandler catch NoHandlerFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(NotFoundErrorCode.NOT_FOUND_END_POINT.getHttpStatus())
                .body(NotFoundErrorCode.NOT_FOUND_END_POINT);
    }

    // 기본 예외
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<InternalServerErrorCode> handleException(Exception e) {
        log.error("handleException() in GlobalExceptionHandler throw Exception : {}", e.getMessage());
        e.printStackTrace();
        return ResponseEntity
                .status(InternalServerErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(InternalServerErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<IllegalArgumentErrorCode> handleException(MethodArgumentNotValidException e) {
        log.error("handleException() in GlobalExceptionHandler throw MethodArgumentNotValidException : {}", e.getMessage());
        return ResponseEntity
                .status(IllegalArgumentErrorCode.INVALID_ARGUMENTS.getHttpStatus())
                .body(IllegalArgumentErrorCode.INVALID_ARGUMENTS);
    }
}

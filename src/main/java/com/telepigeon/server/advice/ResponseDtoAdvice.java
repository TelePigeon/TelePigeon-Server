package com.telepigeon.server.advice;

import com.telepigeon.server.dto.common.ResponseDto;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.exception.code.DefaultErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(
        basePackages = "com.telepigeon.server"
)
public class ResponseDtoAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return !(returnType.getParameterType() == ResponseDto.class)
                && MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        if (body instanceof DefaultErrorCode)
            return ResponseDto.fail((DefaultErrorCode) body);
        return ResponseDto.success(body);
    }
}

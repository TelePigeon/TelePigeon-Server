package com.telepigeon.server.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telepigeon.server.dto.common.ResponseDto;
import com.telepigeon.server.exception.code.DefaultErrorCode;
import com.telepigeon.server.exception.code.UnAuthorizedErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        DefaultErrorCode errorCode = (DefaultErrorCode) request.getAttribute("exception");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (errorCode == null) {
            response.setStatus(UnAuthorizedErrorCode.UNAUTHORIZED.getHttpStatus().value());
            response.getWriter().write(
                    objectMapper.writeValueAsString(ResponseDto.fail(UnAuthorizedErrorCode.UNAUTHORIZED)));
        } else {
            response.setStatus(errorCode.getHttpStatus().value());
            response.getWriter().write(
                    objectMapper.writeValueAsString(ResponseDto.fail(errorCode)));
        }
    }
}

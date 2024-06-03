package com.telepigeon.server.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telepigeon.server.dto.common.ResponseDto;
import com.telepigeon.server.exception.code.DefaultErrorCode;
import com.telepigeon.server.exception.code.UnAuthorizedErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            log.error("FilterException throw MalformedJwtException Exception : {}", e.getMessage());
            handleException(response, UnAuthorizedErrorCode.TOKEN_MALFORMED_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("FilterException throw IllegalArgumentException Exception : {}", e.getMessage());
            handleException(response, UnAuthorizedErrorCode.TOKEN_TYPE_ERROR);
        } catch (ExpiredJwtException e) {
            log.error("FilterException throw ExpiredJwtException Exception : {}", e.getMessage());
            handleException(response, UnAuthorizedErrorCode.TOKEN_EXPIRED_ERROR);
        } catch (UnsupportedJwtException e) {
            log.error("FilterException throw UnsupportedJwtException Exception : {}", e.getMessage());
            handleException(response, UnAuthorizedErrorCode.TOKEN_UNSUPPORTED_ERROR);
        } catch (JwtException e) {
            log.error("FilterException throw JwtException Exception : {}", e.getMessage());
            handleException(response, UnAuthorizedErrorCode.TOKEN_UNKNOWN_ERROR);
        }
    }

    private void handleException(
            HttpServletResponse response,
            DefaultErrorCode errorCode
    ) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getHttpStatus().value());
        response.getWriter().write(
                objectMapper.writeValueAsString(ResponseDto.fail(errorCode)));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return FilterUtil.shouldNotFilter(request);
    }
}

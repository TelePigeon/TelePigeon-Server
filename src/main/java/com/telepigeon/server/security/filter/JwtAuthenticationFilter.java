package com.telepigeon.server.security.filter;

import com.telepigeon.server.constant.AuthConstant;
import com.telepigeon.server.exception.code.IllegalArgumentErrorCode;
import com.telepigeon.server.security.info.UserAuthentication;
import com.telepigeon.server.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String token = getJwtFromRequest(request);
        if (!StringUtils.hasText(token)) {
            request.setAttribute("exception", IllegalArgumentErrorCode.ILLEGAL_ARGUMENT_HEADER);
        } else {
            Claims claims = jwtUtil.getTokenBody(token);
            Long userId = claims.get(AuthConstant.USER_ID_CLAIM_NAME, Long.class);
            UserAuthentication authentication = UserAuthentication.createUserAuthentication(userId);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
       String bearerToken = request.getHeader(AuthConstant.AUTHORIZATION_HEADER);
       if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AuthConstant.BEARER_PREFIX)) {
           return bearerToken.substring(AuthConstant.BEARER_PREFIX.length());
       }
       return null;
    }
}

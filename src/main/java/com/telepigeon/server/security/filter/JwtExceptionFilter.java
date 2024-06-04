package com.telepigeon.server.security.filter;

import com.telepigeon.server.exception.IllegalArgumentException;
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
            filterChain.doFilter(request, response);
        } catch (IllegalArgumentException e) {
            log.error("FilterException throw IllegalArgumentException Exception : {}", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error("FilterException throw ExpiredJwtException Exception : {}", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (UnsupportedJwtException e) {
            log.error("FilterException throw UnsupportedJwtException Exception : {}", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            log.error("FilterException throw JwtException Exception : {}", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("FilterException throw Exception : {}", e.getMessage());
            filterChain.doFilter(request, response);
        }
    }
}

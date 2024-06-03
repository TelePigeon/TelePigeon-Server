package com.telepigeon.server.utils;

import com.telepigeon.server.dto.auth.JwtTokensDto;
import com.telepigeon.server.exception.UnAuthorizedException;
import com.telepigeon.server.exception.code.UnAuthorizedErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil implements InitializingBean {

    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.access-token-expire-period}")
    private Integer accessTokenExpirePeriod;
    @Value("${jwt.refresh-token-expire-period}")
    @Getter
    private Integer refreshTokenExpirePeriod;

    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtTokensDto generateTokens(Long id) {
        return JwtTokensDto.of(
                generateToken(id, accessTokenExpirePeriod),
                generateToken(id, refreshTokenExpirePeriod));
    }

    private String generateToken(Long id, Integer expirePeriod) {
        Claims claims = Jwts.claims();
        claims.put("uid", id);

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirePeriod))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims getTokenBody(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException ex) {
            throw new UnAuthorizedException(UnAuthorizedErrorCode.INVALID_JWT);
        } catch (ExpiredJwtException ex) {
            throw new UnAuthorizedException(UnAuthorizedErrorCode.EXPIRED_JWT);
        } catch (UnsupportedJwtException ex) {
            throw new UnAuthorizedException(UnAuthorizedErrorCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException ex) {
            throw new UnAuthorizedException(UnAuthorizedErrorCode.JWT_IS_EMPTY);
        }
    }
}

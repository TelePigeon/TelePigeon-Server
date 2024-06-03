package com.telepigeon.server.service.auth;

import com.telepigeon.server.domain.Token;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRetriever {

    private final TokenRepository tokenRepository;

    public Token findIdByRefreshToken(final String refreshToken){
        return tokenRepository.findIdByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException(NotFoundErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }
}

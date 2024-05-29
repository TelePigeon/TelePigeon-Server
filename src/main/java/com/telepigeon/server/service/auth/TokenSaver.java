package com.telepigeon.server.service.auth;

import com.telepigeon.server.domain.Token;
import com.telepigeon.server.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenSaver {

    private final TokenRepository tokenRepository;

    public void save(final Token token){
        tokenRepository.save(token);
    }
}

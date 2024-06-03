package com.telepigeon.server.service.auth;

import com.telepigeon.server.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRemover {

    private final TokenRepository tokenRepository;

    public void removeById (final Long id){
        tokenRepository.deleteById(id);
    }

}

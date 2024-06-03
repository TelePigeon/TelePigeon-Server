package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Long> {

    Optional<Token> findIdByRefreshToken(String refreshToken);
}

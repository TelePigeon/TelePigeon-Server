package com.telepigeon.server.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value="token", timeToLive = 60 * 60 * 24 * 14)
@NoArgsConstructor
public class Token {

    @Id
    private Long id;

    @Indexed
    private String refreshToken;

    private Token(Long id, String refreshToken) {
        this.id = id;
        this.refreshToken = refreshToken;
    }

    public static Token create(Long id, String refreshToken) {
        return new Token(id, refreshToken);
    }
}

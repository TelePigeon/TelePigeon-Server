package com.telepigeon.server.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@RedisHash(value="hurry")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Hurry {
    @Id
    private String profileId;
    private LocalDateTime createdAt;

    public static Hurry create(String profileId){
        return new Hurry(profileId);
    }

    private Hurry(String profileId){
        this.profileId = profileId;
        this.createdAt = LocalDateTime.now();
    }
}

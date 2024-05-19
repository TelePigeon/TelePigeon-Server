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
    private String roomAndSender;
    private LocalDateTime createdAt;

    public static Hurry create(Long roomId, Long senderId){
        return new Hurry(roomId + ":" + senderId);
    }

    private Hurry(String roomAndSender){
        this.roomAndSender = roomAndSender;
        this.createdAt = LocalDateTime.now();
    }
}

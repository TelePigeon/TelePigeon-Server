package com.telepigeon.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@RedisHash(value="hurry")
public class Hurry {
    @Id
    private String RoomAndSender;
    private LocalDateTime createdAt;

    public static Hurry create(Long roomId, Long senderId){
        return new Hurry(roomId + ":" + senderId, LocalDateTime.now());
    }
}

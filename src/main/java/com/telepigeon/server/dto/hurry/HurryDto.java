package com.telepigeon.server.dto.hurry;

import com.telepigeon.server.domain.Hurry;

import java.time.LocalDateTime;

public record HurryDto(
        Long roomId,
        Long senderId,
        LocalDateTime createdAt
) {
    public static HurryDto of(Hurry hurry){
        return new HurryDto(Long.valueOf(
                hurry.getRoomAndSender().split(":")[0]),
                Long.valueOf(hurry.getRoomAndSender().split(":")[1]),
                hurry.getCreatedAt()
        );
    }
}

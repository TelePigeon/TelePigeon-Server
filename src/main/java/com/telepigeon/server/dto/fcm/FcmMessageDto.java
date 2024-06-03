package com.telepigeon.server.dto.fcm;

import com.telepigeon.server.dto.type.FcmContent;
import lombok.Builder;

@Builder
public record FcmMessageDto(
        String title,
        String body,
        String clickAction,
        String type,
        Long id
){
    public static FcmMessageDto of(FcmContent fcmContent, Long id){
        return FcmMessageDto.builder()
                .title(fcmContent.getTitle())
                .body(fcmContent.getBody())
                .clickAction(fcmContent.getBody())
                .type(fcmContent.getType())
                .id(id)
                .build();
    }
}

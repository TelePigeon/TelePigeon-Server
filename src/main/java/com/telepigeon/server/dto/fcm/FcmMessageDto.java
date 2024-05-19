package com.telepigeon.server.dto.fcm;

import lombok.Builder;

@Builder
public record FcmMessageDto(
        String title,
        String body,
        String clickAction,
        String type,
        Long id
){
    public static FcmMessageDto of(String title, String body, String clickAction, String type, Long id){
        return FcmMessageDto.builder()
                .title(title)
                .body(body)
                .clickAction(clickAction)
                .type(type)
                .id(id)
                .build();
    }
}

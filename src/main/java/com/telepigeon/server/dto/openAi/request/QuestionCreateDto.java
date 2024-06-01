package com.telepigeon.server.dto.openAi.request;

import com.telepigeon.server.dto.openAi.MessageDto;

import java.util.List;

public record QuestionCreateDto(
        String model,
        List<MessageDto> messages,
        int max_tokens,
        double temperature
) {
    public static QuestionCreateDto of(String model, List<MessageDto> messages){
        return new QuestionCreateDto(model, messages, 256, 0.7);
    }
}

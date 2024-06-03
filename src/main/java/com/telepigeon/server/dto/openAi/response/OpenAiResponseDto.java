package com.telepigeon.server.dto.openAi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telepigeon.server.dto.openAi.MessageDto;

import java.util.List;

public record OpenAiResponseDto (
    List<ChoiceDto> choices
) {
    public record ChoiceDto(
            @JsonProperty(value = "finish_reason")
            String finishReason,
            String index,
            MessageDto message,
            Object logprobs
    ) {
    }
}

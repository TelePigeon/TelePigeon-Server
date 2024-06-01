package com.telepigeon.server.dto.openAi.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.telepigeon.server.dto.openAi.MessageDto;

public record ChoiceDto(
        @JsonProperty(value = "finish_reason")
        String finishReason,
        String index,
        MessageDto message,
        Object logprobs
) {
}

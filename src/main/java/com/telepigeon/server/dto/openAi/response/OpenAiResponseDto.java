package com.telepigeon.server.dto.openAi.response;

import java.util.List;

public record OpenAiResponseDto (
    List<ChoiceDto> choices
){
}

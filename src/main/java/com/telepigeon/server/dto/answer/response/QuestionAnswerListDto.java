package com.telepigeon.server.dto.answer.response;

import java.util.List;

public record QuestionAnswerListDto(
        List<QuestionAnswerDto> sets
) {
    public static QuestionAnswerListDto of(List<QuestionAnswerDto> questionAnswerDtoList){
        return new QuestionAnswerListDto(questionAnswerDtoList);
    }
}

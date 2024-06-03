package com.telepigeon.server.dto.answer.response;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.domain.Question;

public record QuestionAnswerDto(
        String questionName,
        String answerName,
        String questionContent,
        String answerContent,
        String answerImage
) {
    public static QuestionAnswerDto of(
            Question question,
            Answer answer
    ) {
        return new QuestionAnswerDto(
                question.getProfile().getUser().getName(),
                answer.getProfile().getUser().getName(),
                question.getContent(),
                answer.getContent(),
                answer.getImage()
        );
    }
}

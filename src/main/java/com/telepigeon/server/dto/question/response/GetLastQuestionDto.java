package com.telepigeon.server.dto.question.response;

import com.telepigeon.server.domain.Question;

public record GetLastQuestionDto(
        Long id,
        String content,
        boolean isPenalty
) {
    public static GetLastQuestionDto of(
            Question question,
            boolean isPenalty
    ) {
        return new GetLastQuestionDto(
                question.getId(),
                question.getContent(),
                isPenalty
        );
    }
}

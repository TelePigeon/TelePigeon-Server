package com.telepigeon.server.dto.question.response;

import com.telepigeon.server.domain.Question;

public record GetLastQuestionDto(
        Long id,
        String content,
        boolean isPenalty,
        boolean easyMode
) {
    public static GetLastQuestionDto of(
            Question question,
            boolean isPenalty,
            boolean easyMode
    ) {
        return new GetLastQuestionDto(
                question.getId(),
                question.getContent(),
                isPenalty,
                easyMode
        );
    }
}

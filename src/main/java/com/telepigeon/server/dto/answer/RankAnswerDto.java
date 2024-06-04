package com.telepigeon.server.dto.answer;

public record RankAnswerDto(
        String keyword,
        Double emotion
) {
    public static RankAnswerDto of(
            String keyword,
            Double emotion
    ){
        return new RankAnswerDto(keyword, emotion);
    }
}

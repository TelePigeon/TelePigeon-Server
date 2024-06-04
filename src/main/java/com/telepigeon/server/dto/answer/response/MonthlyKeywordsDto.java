package com.telepigeon.server.dto.answer.response;

import java.util.List;

public record MonthlyKeywordsDto(
        List<String> positiveKeywords,
        List<String> negativeKeywords
) {
    public static MonthlyKeywordsDto of(
            List<String> positiveKeywords,
            List<String> negativeKeywords
    ){
        return new MonthlyKeywordsDto(positiveKeywords, negativeKeywords);
    }
}

package com.telepigeon.server.dto.naverCloud;

public record ConfidenceDto(
        Double neutral,
        Double positive,
        Double negative
) {
    public static ConfidenceDto of(Double neutral, Double positive, Double negative){
        return new ConfidenceDto(neutral, positive, negative);
    }
}

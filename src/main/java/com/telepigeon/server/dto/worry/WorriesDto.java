package com.telepigeon.server.dto.worry;

import com.telepigeon.server.domain.Worry;

import java.util.Arrays;
import java.util.List;

public record WorriesDto (
        List<WorryDto> worries
) {

    public static WorriesDto of(List<Worry> worries) {
        return new WorriesDto(
                worries.stream()
                        .map(WorryDto::of)
                        .toList()
        );
    }

    public record WorryDto(
            Long id,
            String name,
            String content,
            List<String> times
    ) {
        public static WorryDto of(Worry worry) {
            return new WorryDto(
                    worry.getId(),
                    worry.getName(),
                    worry.getContent(),
                    Arrays.stream(worry.getTimes().split("\\|")).toList()
            );
        }
    }
}

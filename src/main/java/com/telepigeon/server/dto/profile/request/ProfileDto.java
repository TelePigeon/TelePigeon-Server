package com.telepigeon.server.dto.profile.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProfileDto(
        @Nullable
        List<String> keywords,
        @Nullable
        String gender,
        @Nullable
        String ageRange,
        @Nullable
        String relation,
        @NotNull
        boolean easyMode
) {
}

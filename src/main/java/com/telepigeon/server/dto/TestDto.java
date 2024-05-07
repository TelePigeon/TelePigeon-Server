package com.telepigeon.server.dto;

public record TestDto(
    String test
) {
    public static TestDto of (String test) {
        return new TestDto(test);
    }
}

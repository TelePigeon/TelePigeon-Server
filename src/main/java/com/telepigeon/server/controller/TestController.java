package com.telepigeon.server.controller;

import com.telepigeon.server.dto.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/exception2")
    public ApiResponse<?> testException() {
        throw new RuntimeException();
    }
}

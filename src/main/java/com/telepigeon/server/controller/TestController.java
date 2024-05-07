package com.telepigeon.server.controller;

import com.telepigeon.server.dto.TestDto;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test/1")
    public ResponseEntity<TestDto> test1() {
        return ResponseEntity.ok(TestDto.of("test1"));
    }

    @GetMapping("/test/2")
    public ResponseEntity<Void> test2() {
        return ResponseEntity.created(URI.create("/test/2")).build();
    }

    @GetMapping("/test/exception1")
    public void testBusinessException() {
        throw new BusinessException(BusinessErrorCode.BUSINESS_TEST);
    }

    @GetMapping("/test/exception2")
    public void testException() {
        throw new RuntimeException();
    }
}

package com.telepigeon.server.controller;

import com.telepigeon.server.domain.Token;
import com.telepigeon.server.dto.TestDto;
import com.telepigeon.server.dto.auth.response.JwtTokensDto;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.service.auth.TokenSaver;
import com.telepigeon.server.service.openAi.OpenAiService;
import com.telepigeon.server.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TestController {

    private final JwtUtil jwtUtil;
    private final TokenSaver tokenSaver;
    private final OpenAiService openAiService;

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

    @GetMapping("/test/token/{userId}")
    public ResponseEntity<JwtTokensDto> generateToken(@PathVariable Long userId) {
        JwtTokensDto tokens = jwtUtil.generateTokens(userId);
        tokenSaver.save(Token.create(userId, tokens.refreshToken()));
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("test/open-ai")
    public ResponseEntity<String> testOpenAi(
            @RequestParam String relation,
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(openAiService.createQuestion(relation, keyword));
    }
}

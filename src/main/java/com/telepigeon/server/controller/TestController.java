package com.telepigeon.server.controller;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Token;
import com.telepigeon.server.dto.TestDto;
import com.telepigeon.server.dto.auth.response.JwtTokensDto;
import com.telepigeon.server.dto.naverCloud.ConfidenceDto;
import com.telepigeon.server.dto.naverCloud.request.ConfidenceCreateDto;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.service.auth.TokenSaver;
import com.telepigeon.server.service.external.NaverCloudService;
import com.telepigeon.server.service.openAi.OpenAiService;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.question.QuestionService;
import com.telepigeon.server.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TestController {

    private final JwtUtil jwtUtil;
    private final TokenSaver tokenSaver;
    private final OpenAiService openAiService;
    private final NaverCloudService naverCloudService;
    private final QuestionService questionService;
    private final ProfileRetriever profileRetriever;

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

    @GetMapping("/test/open-ai")
    public ResponseEntity<String> testOpenAi(
            @RequestParam String relation,
            @RequestParam String keyword,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String ageRange,
            @RequestParam boolean easyMode
    ) {
        String question = openAiService.createQuestion(relation, keyword, gender, ageRange, easyMode);
        log.info("\n질문 생성 완료.\n입력 정보 : 관계 - {} / 키워드 - {} / 성별 - {} / 연령대 - {} / 쉬운 사용 모드 - {}\n내용 : {}", relation, keyword, gender, ageRange, easyMode, question);
        return ResponseEntity.ok(question);
    }

    @PostMapping("/test/question")
    public ResponseEntity<Void> testQuestion(
    ){
        Profile profile1 = profileRetriever.findById(92L);
        Profile profile2 = profileRetriever.findById(93L);
        questionService.create(profile1);
        questionService.create(profile2);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test/emotion")
    public ResponseEntity<ConfidenceDto> testEmotion(
            @RequestBody ConfidenceCreateDto text
    ) {
        return ResponseEntity.ok(naverCloudService.getConfidence(text));
    }
}

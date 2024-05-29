package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.dto.question.response.GetLastQuestionDto;
import com.telepigeon.server.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/rooms/{roomId}/questions")
    public ResponseEntity<GetLastQuestionDto> getLastQuestion(
            @UserId Long userId,
            @PathVariable Long roomId
    ) {
        return ResponseEntity.ok(questionService.findLastQuestion(userId, roomId));
    }
}

package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.dto.answer.request.AnswerCreateDto;
import com.telepigeon.server.dto.answer.response.QuestionAnswerListDto;
import com.telepigeon.server.service.answer.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/rooms/{roomId}/questions/{questionId}/answers")
    public ResponseEntity<Void> postAnswer(
            @UserId Long userId,
            @PathVariable Long roomId,
            @PathVariable Long questionId,
            @RequestBody @Valid AnswerCreateDto answerCreateDto
    ) {
        return ResponseEntity.created(
                URI.create(
                        "/answers/" + answerService.create(
                                userId,
                                roomId,
                                questionId,
                                answerCreateDto
                        )
                )
        ).build();
    }

    @GetMapping("/rooms/{roomId}/talks")
    public ResponseEntity<QuestionAnswerListDto> getAllQuestionAndAnswerByDate(
            @UserId Long userId,
            @PathVariable Long roomId,
            @RequestParam(required=false) LocalDate date,
            @RequestParam boolean respondent
    ) {
        return ResponseEntity.ok(
                answerService.getAllQuestionAndAnswerByDate(
                        userId,
                        roomId,
                        date,
                        respondent
                )
        );
    }
}

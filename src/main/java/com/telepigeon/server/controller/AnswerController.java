package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.dto.answer.request.AnswerCreateDto;
import com.telepigeon.server.dto.answer.response.MonthlyKeywordsDto;
import com.telepigeon.server.dto.answer.response.QuestionAnswerListDto;
import com.telepigeon.server.dto.room.response.RoomStateDto;
import com.telepigeon.server.exception.IllegalArgumentException;
import com.telepigeon.server.exception.code.IllegalArgumentErrorCode;
import com.telepigeon.server.service.answer.AnswerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.YearMonth;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/rooms/{roomId}/questions/{questionId}/answers")
    public ResponseEntity<Void> postAnswer(
            @UserId final Long userId,
            @PathVariable final Long roomId,
            @PathVariable final Long questionId,
            @ModelAttribute @Valid final AnswerCreateDto answerCreateDto
    ) throws IOException {
        return ResponseEntity.created(
                URI.create(
                        "/answers/" + answerService.create(
                                userId,
                                roomId,
                                questionId,
                                answerCreateDto
                        ).getId()
                )
        ).build();
    }

    @GetMapping("/rooms/{roomId}/talks")
    public ResponseEntity<QuestionAnswerListDto> getAllQuestionAndAnswerByDate(
            @UserId final Long userId,
            @PathVariable final Long roomId,
            @RequestParam(required=false) final LocalDate date,
            @RequestParam final boolean respondent
    ) {
        if (!respondent && date == null)
            throw new IllegalArgumentException(IllegalArgumentErrorCode.ILLEGAL_ARGUMENT_DATE);
        return ResponseEntity.ok(
                answerService.getAllQuestionAndAnswerByDate(
                        userId,
                        roomId,
                        date,
                        respondent
                )
        );
    }

    @GetMapping("/rooms/{roomId}")
    ResponseEntity<RoomStateDto> getRoomState(
            @UserId final Long userId,
            @PathVariable final Long roomId
    ) {
        return ResponseEntity.ok(
                answerService.getRoomState(userId, roomId)
        );
    }

    @GetMapping("/rooms/{roomId}/reports")
    ResponseEntity<MonthlyKeywordsDto> getMonthlyKeywords(
            @UserId final Long userId,
            @PathVariable final Long roomId,
            @RequestParam final YearMonth date
            ) {
        return ResponseEntity.ok(
                answerService.getMonthlyKeywords(userId, roomId, date)
        );
    }
}

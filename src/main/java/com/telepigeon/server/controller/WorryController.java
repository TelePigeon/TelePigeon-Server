package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.service.worry.WorryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WorryController {

    private final WorryService worryService;

    @GetMapping("/rooms/{roomId}/worries")
    public ResponseEntity<?> getWorries(
            @UserId final Long userId,
            @PathVariable final Long roomId
    ) {
        return ResponseEntity.ok(worryService.getWorries(userId, roomId));
    }
}

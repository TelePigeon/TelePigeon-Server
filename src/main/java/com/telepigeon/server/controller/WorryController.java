package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.dto.worry.request.WorryCreateDto;
import com.telepigeon.server.dto.worry.response.WorriesDto;
import com.telepigeon.server.service.worry.WorryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WorryController {

    private final WorryService worryService;

    @GetMapping("/rooms/{roomId}/worries")
    public ResponseEntity<WorriesDto> getWorries(
            @UserId final Long userId,
            @PathVariable final Long roomId
    ) {
        return ResponseEntity.ok(worryService.getWorries(userId, roomId));
    }

    @PostMapping("/rooms/{roomId}/worries")
    public ResponseEntity<Void> createWorry(
            @UserId final Long userId,
            @PathVariable final Long roomId,
            @RequestBody final WorryCreateDto request
    ) {
        worryService.createWorry(userId, roomId, request);
        return ResponseEntity.created(URI.create("/rooms/"+ roomId + "/worries")).build();
    }

    @DeleteMapping("/worries/{worryId}")
    public ResponseEntity<Void> deleteWorry(
            @UserId final Long userId,
            @PathVariable final Long worryId
    ) {
        worryService.deleteWorry(userId, worryId);
        return ResponseEntity.noContent().build();
    }
}

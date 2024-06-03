package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.service.hurry.HurryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HurryController {
    private final HurryService hurryService;

    @PostMapping("/rooms/{roomId}/hurries")
    public ResponseEntity<Void> create(
            @UserId final Long userId,
            @PathVariable final Long roomId
    ) {
        hurryService.create(userId, roomId);
        return ResponseEntity.created(URI.create("/hurry/")).build();
    }
}

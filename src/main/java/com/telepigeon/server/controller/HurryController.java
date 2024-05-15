package com.telepigeon.server.controller;

import com.telepigeon.server.service.hurry.HurryRetriever;
import com.telepigeon.server.service.hurry.HurryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{roomId}/hurries")
public class HurryController {
    private final HurryService hurryService;
    private final HurryRetriever hurryRetriever;

    @PostMapping
    public ResponseEntity<Void> create(
            @PathVariable Long roomId
    ){
        hurryService.create(roomId);
        return ResponseEntity.created(URI.create("hurry")).build();
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Void> find(
            @PathVariable Long roomId,
            @PathVariable Long userId
    ){
        hurryRetriever.findByRoomIdAndSenderId(roomId, userId);
        return ResponseEntity.ok().build();
    }
}

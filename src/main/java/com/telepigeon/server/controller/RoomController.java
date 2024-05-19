package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Void> createRoom(
            @UserId Long userId,
            @RequestBody RoomCreateDto roomCreateDto
            ) {
        roomService.createRoom(roomCreateDto);
        return ResponseEntity.created(URI.create("room")).build();
    }
}

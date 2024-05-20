package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.service.room.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/rooms")
    public ResponseEntity<Void> createRoom(
            @UserId Long userId,
            @Valid @RequestBody RoomCreateDto roomCreateDto
            ) {
        Room createdRoom = roomService.createRoom(roomCreateDto, userId);
        URI location = URI.create("/rooms/" + createdRoom.getId());
        return ResponseEntity.created(location).build();
    }
}

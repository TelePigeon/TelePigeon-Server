package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.dto.room.response.RoomListDto;
import com.telepigeon.server.service.room.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomListDto>> getAllRooms(@UserId Long userId) {
        return ResponseEntity.ok(roomService.getAllRooms(userId));
    }
}

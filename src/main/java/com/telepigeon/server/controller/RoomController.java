package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.dto.room.request.RoomEnterDto;
import com.telepigeon.server.dto.room.response.RoomInfoDto;
import com.telepigeon.server.dto.room.response.RoomListDto;
import com.telepigeon.server.service.room.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/rooms")
    public ResponseEntity<Void> createRoom(
            @UserId final Long userId,
            @Valid @RequestBody final RoomCreateDto roomCreateDto
    ) {
        Room createdRoom = roomService.createRoom(roomCreateDto, userId);
        URI location = URI.create("/rooms/" + createdRoom.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/rooms")
    public ResponseEntity<RoomListDto> getAllRooms(@UserId final Long userId) {
        return ResponseEntity.ok(roomService.getAllRooms(userId));
    }

    @GetMapping("/rooms/{roomId}/info")
    public ResponseEntity<RoomInfoDto> getRoomInfo(@PathVariable final Long roomId) {
        return ResponseEntity.ok(roomService.getRoomInfo(roomId));
    }

    @PostMapping("/rooms/entrance")
    public ResponseEntity<Void> enterRoom(
            @UserId final Long userId,
            @Valid @RequestBody final RoomEnterDto roomEnterDto
    ) {
        Profile createProfile = roomService.enterRoom(roomEnterDto, userId);
        return ResponseEntity.created(URI.create("/profiles/" + createProfile.getId())).build();
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoom(
            @UserId final Long userId,
            @PathVariable final Long roomId
    ) {
        roomService.deleteRoom(roomId, userId);
        return ResponseEntity.ok().build();
    }
}

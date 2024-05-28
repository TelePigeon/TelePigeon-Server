package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.dto.profile.response.ProfileInfoDto;
import com.telepigeon.server.dto.profile.response.ProfileKeywordDto;
import com.telepigeon.server.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/rooms/{roomId}/keywords")
    public ResponseEntity<ProfileKeywordDto> getProfileKeyword(
            @PathVariable Long roomId,
            @UserId Long userId
    ) {
        return ResponseEntity.ok(profileService.getProfileKeyword(roomId, userId));
    }

    @GetMapping("/rooms/{roomId}/extra")
    public ResponseEntity<ProfileInfoDto> getProfileExtraInfo(
            @PathVariable Long roomId,
            @UserId Long userId
    ) {
        return ResponseEntity.ok(profileService.getProfileExtraInfo(roomId, userId));
    }
}

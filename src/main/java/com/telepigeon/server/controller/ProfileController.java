package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.dto.profile.request.ProfileDto;
import com.telepigeon.server.dto.profile.response.ProfileInfoDto;
import com.telepigeon.server.dto.profile.response.ProfileKeywordDto;
import com.telepigeon.server.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.objenesis.SpringObjenesis;

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

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<Profile> updateProfileInfo(
            @PathVariable Long roomId,
            @UserId Long userId,
            @Valid @RequestBody ProfileDto profileDto
    ) {
        return ResponseEntity.ok(profileService.updateProfileInfo(roomId, userId, profileDto));
    }
}

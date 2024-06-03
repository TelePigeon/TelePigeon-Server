package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.dto.profile.request.ProfileDto;
import com.telepigeon.server.dto.profile.response.ProfileInfoDto;
import com.telepigeon.server.dto.profile.response.ProfileKeywordsDto;
import com.telepigeon.server.service.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/rooms/{roomId}/keywords")
    public ResponseEntity<ProfileKeywordsDto> getProfileKeyword(
            @PathVariable Long roomId,
            @UserId Long userId
    ) {
        return ResponseEntity.ok(profileService.getProfileKeywords(roomId, userId));
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
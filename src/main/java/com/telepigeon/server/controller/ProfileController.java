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

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/rooms/{roomId}/keywords")
    public ResponseEntity<ProfileKeywordsDto> getProfileKeyword(
            @PathVariable final Long roomId,
            @UserId final Long userId
    ) {
        return ResponseEntity.ok(profileService.getProfileKeywords(roomId, userId));
    }

    @GetMapping("/rooms/{roomId}/extra")
    public ResponseEntity<ProfileInfoDto> getProfileExtraInfo(
            @PathVariable final Long roomId,
            @UserId final Long userId
    ) {
        return ResponseEntity.ok(profileService.getProfileExtraInfo(roomId, userId));
    }

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<Void> updateProfileInfo(
            @PathVariable final Long roomId,
            @UserId final Long userId,
            @Valid @RequestBody final ProfileDto profileDto
    ) {
        profileService.updateProfileInfo(roomId, userId, profileDto);
        return ResponseEntity.ok().build();
    }
}

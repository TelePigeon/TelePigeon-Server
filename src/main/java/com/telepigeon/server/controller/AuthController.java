package com.telepigeon.server.controller;

import com.telepigeon.server.dto.auth.JwtTokensDto;
import com.telepigeon.server.service.auth.AuthService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login/kakao")
    public ResponseEntity<JwtTokensDto> KakaoLogin(
            @NotNull @RequestHeader("Authorization") String kakaoToken
    ) {
        return ResponseEntity.ok(authService.login(kakaoToken));
    }

}

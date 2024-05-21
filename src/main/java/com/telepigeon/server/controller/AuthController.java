package com.telepigeon.server.controller;

import com.telepigeon.server.dto.auth.JwtTokensDto;
import com.telepigeon.server.service.auth.AuthService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login/kakao")
    public JwtTokensDto KakaoLogin(
            @NotNull @RequestHeader("Authorization") String kakaoToken
    ) {
        return authService.login(kakaoToken);
    }

}

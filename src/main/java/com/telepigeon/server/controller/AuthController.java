package com.telepigeon.server.controller;

import com.telepigeon.server.annotation.UserId;
import com.telepigeon.server.constant.AuthConstant;
import com.telepigeon.server.dto.auth.response.JwtTokensDto;
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
            @NotNull @RequestHeader(AuthConstant.AUTHORIZATION_HEADER) final String kakaoToken
    ) {
        return ResponseEntity.ok(authService.login(kakaoToken));
    }

    @DeleteMapping("/auth/logout")
    public ResponseEntity<Void> logout(@UserId final Long userId) {
        authService.logout(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/auth/withdrawal")
    public ResponseEntity<Void> withdrawal(@UserId final Long userId) {
        authService.withdrawal(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/auth/reissue")
    public ResponseEntity<JwtTokensDto> reissue(
            @NotNull @RequestHeader(AuthConstant.AUTHORIZATION_HEADER) final String Authorization
    ) {
        return ResponseEntity.ok(authService.reissue(Authorization));
    }

}

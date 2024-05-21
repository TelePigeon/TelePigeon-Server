package com.telepigeon.server.oauth.service;

import com.telepigeon.server.dto.auth.SocialUserInfoDto;
import com.telepigeon.server.oauth.dto.KakaoUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class KakaoService {

    @Value("${kakao.user-info-url}")
    private String kakaoUserInfoUrl;

    public SocialUserInfoDto getUserInfo(final String token) {
        KakaoUserDto kakaoUserDto = requestUserInfo(token);
        return SocialUserInfoDto.of(
                kakaoUserDto.id().toString(),
                kakaoUserDto.kakaoAccount().email(),
                kakaoUserDto.kakaoAccount().kakaoUserProfile().nickname()
        );
    }

    private KakaoUserDto requestUserInfo(final String token) {
        RestClient restClient = RestClient.create();

        return restClient.get()
                .uri(kakaoUserInfoUrl)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(KakaoUserDto.class);
    }
}

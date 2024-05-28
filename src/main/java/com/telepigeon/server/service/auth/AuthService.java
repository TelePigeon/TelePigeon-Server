package com.telepigeon.server.service.auth;

import com.telepigeon.server.domain.User;
import com.telepigeon.server.dto.auth.JwtTokensDto;
import com.telepigeon.server.dto.auth.SocialUserInfoDto;
import com.telepigeon.server.oauth.service.KakaoService;
import com.telepigeon.server.service.user.UserRetriever;
import com.telepigeon.server.service.user.UserSaver;
import com.telepigeon.server.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoService kakaoService;
    private final UserRetriever userRetreiver;
    private final UserSaver userSaver;
    private final UserRemover userRemover;
    private final TokenSaver tokenSaver;
    private final TokenRemover tokenRemover;
    private final JwtUtil jwtUtil;

    @Transactional
    public JwtTokensDto login(final String token){
        SocialUserInfoDto socialUserInfo = kakaoService.getUserInfo(token);
        User user = loadOrCreateKakaoUser(socialUserInfo);
        JwtTokensDto tokens = jwtUtil.generateTokens(user.getId());
        tokenSaver.save(Token.create(user.getId(), tokens.refreshToken()));
        return tokens;
    }

    @Transactional
    public void logout(final Long userId) {
        tokenRemover.removeById(userId);
    }

    private User loadOrCreateKakaoUser(final SocialUserInfoDto socialUserInfo) {
        boolean isRegistered = userRetreiver.existBySerialIdAndProvider(
                socialUserInfo.serialId(),
                "kakao"
        );

        if (!isRegistered){
            User newUser = User.create(
                    socialUserInfo.serialId(),
                    socialUserInfo.email(),
                    socialUserInfo.name(),
                    "kakao"
            );
            userSaver.save(newUser);
        }

        return userRetreiver.findBySerialIdAndProvider(
                socialUserInfo.serialId(),
                "kakao"
        );
    }

}

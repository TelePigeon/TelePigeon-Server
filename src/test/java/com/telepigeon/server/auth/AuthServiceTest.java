package com.telepigeon.server.auth;

import com.telepigeon.server.domain.User;
import com.telepigeon.server.dto.auth.JwtTokensDto;
import com.telepigeon.server.dto.auth.SocialUserInfoDto;
import com.telepigeon.server.exception.UnAuthorizedException;
import com.telepigeon.server.exception.code.UnAuthorizedErrorCode;
import com.telepigeon.server.oauth.service.KakaoService;
import com.telepigeon.server.service.auth.AuthService;
import com.telepigeon.server.service.user.UserRetriever;
import com.telepigeon.server.service.user.UserSaver;
import com.telepigeon.server.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Field;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @MockBean
    private KakaoService kakaoService;

    @MockBean
    private UserRetriever userRetriever;

    @MockBean
    private UserSaver userSaver;

    private String token;
    private SocialUserInfoDto socialUserInfo;
    private User user;
    private Field idField;

    @Nested
    @DisplayName("카카오 로그인/회원가입 테스트")
    class KakaoLoginTest {

        @Test
        @DisplayName("카카오 회원가입 성공")
        void kakaoRegisterSuccessTest() throws Exception {
            // given
            kakaoLoginSetup();
            given(userRetriever.existBySerialIdAndProvider("123456", "kakao")).willReturn(false);
            given(userRetriever.findBySerialIdAndProvider("123456", "kakao")).willReturn(user);

            ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
            given(userSaver.save(userCaptor.capture())).willAnswer(
                    invocation -> {
                        User capturedUser = userCaptor.getValue();
                        idField.set(capturedUser, 1L);
                        return capturedUser;
                    }
            );

            // when
            JwtTokensDto jwts = authService.login(token);
            Claims claims = jwtUtil.getTokenBody(jwts.accessToken());

            // then
            Assertions.assertThat(claims.get("uid", Long.class))
                    .isEqualTo(1L);
        }

        @Test
        @DisplayName("카카오 로그인 성공")
        void kakaoLoginSuccessTest() throws Exception{
            // given
            kakaoLoginSetup();
            given(userRetriever.existBySerialIdAndProvider("123456", "kakao")).willReturn(true);
            given(userRetriever.findBySerialIdAndProvider("123456", "kakao")).willReturn(user);

            // when
            JwtTokensDto jwts = authService.login(token);
            Claims claims = jwtUtil.getTokenBody(jwts.accessToken());

            // then
            Assertions.assertThat(claims.get("uid", Long.class))
                    .isEqualTo(1L);
        }

        @Test
        @DisplayName("잘못된 토큰 사용")
        void invalidKakaoTokenTest() throws Exception{
            //given
            kakaoLoginSetup();
            String invalidToken = "invalidToken";

            // when
            Throwable thrown = Assertions.catchThrowable(() -> authService.login(invalidToken));

            // then
            Assertions.assertThat(thrown).isInstanceOf(UnAuthorizedException.class);
            Assertions.assertThat(((UnAuthorizedException) thrown).getErrorCode())
                    .isEqualTo(UnAuthorizedErrorCode.INVALID_KAKAO_TOKEN);
        }

    }

    void kakaoLoginSetup() throws Exception {
        token = "validAccessToken";
        socialUserInfo = SocialUserInfoDto.of("123456", "테스트", "test@gmail.com");
        user = User.create("123456", "테스트", "kakao", "test@gamil.com");

        idField = User.class.getDeclaredField("id"); // private인 id를 설정하지 못해 리플렉션 사용
        idField.setAccessible(true);
        idField.set(user, 1L);

        // unnecessary stubbing 방지를 위한 lenient 사용
        lenient().when(kakaoService.getUserInfo(token)).thenReturn(socialUserInfo);
        lenient().when(kakaoService.getUserInfo(argThat(t -> !token.equals(t))))
                .thenThrow(new UnAuthorizedException(UnAuthorizedErrorCode.INVALID_KAKAO_TOKEN));
    }

}

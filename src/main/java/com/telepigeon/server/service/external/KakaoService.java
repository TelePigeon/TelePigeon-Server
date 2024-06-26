package com.telepigeon.server.service.external;

import com.telepigeon.server.constant.AuthConstant;
import com.telepigeon.server.domain.User;
import com.telepigeon.server.dto.auth.SocialUserInfoDto;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.UnAuthorizedException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.exception.code.UnAuthorizedErrorCode;
import com.telepigeon.server.dto.oauth.request.KakaoUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KakaoService {

    @Value("${kakao.admin-key}")
    private String kakaoAdminKey;

    @Value("${kakao.user-info-url}")
    private String kakaoUserInfoUrl;

    @Value("${kakao.unlink-url}")
    private String kakaoUnlinkUrl;


    public SocialUserInfoDto getUserInfo(final String token) {
        KakaoUserDto kakaoUserDto = requestUserInfo(token);
        return SocialUserInfoDto.of(
                kakaoUserDto.id().toString(),
                kakaoUserDto.kakaoAccount().email(),
                kakaoUserDto.kakaoAccount().kakaoUserProfile().nickname()
        );
    }

    public void unlink(final User user) {
        RestClient restClient = RestClient.create();

        MultiValueMap<String, String> unlinkBody = new LinkedMultiValueMap<>();
        unlinkBody.add("target_id_type", "user_id");
        unlinkBody.add("target_id", user.getSerialId());

        restClient.post()
                .uri(kakaoUnlinkUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(AuthConstant.AUTHORIZATION_HEADER, "KakaoAK " + kakaoAdminKey)
                .body(unlinkBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new BusinessException(BusinessErrorCode.INVALID_KAKAO_ADMIN_KEY);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new BusinessException(BusinessErrorCode.KAKAO_SERVER_ERROR);
                })
                .body(Void.class);
    }

    private KakaoUserDto requestUserInfo(final String token) {
        RestClient restClient = RestClient.create();

        return restClient.get()
                .uri(kakaoUserInfoUrl)
                .header(AuthConstant.AUTHORIZATION_HEADER, AuthConstant.BEARER_PREFIX + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new UnAuthorizedException(UnAuthorizedErrorCode.INVALID_KAKAO_TOKEN);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new BusinessException(BusinessErrorCode.KAKAO_SERVER_ERROR);
                })
                .body(KakaoUserDto.class);
    }

}

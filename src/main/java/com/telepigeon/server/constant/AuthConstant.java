package com.telepigeon.server.constant;

public class AuthConstant {

    public static final String USER_ID_CLAIM_NAME = "uid";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/login/kakao",
            "/api/v1/test/**",
            "/api/v1/keywords",
            "/api/v1/genders",
            "/api/v1/age-ranges",
            "/api/v1/relations",
            // generated
            "/actuator/health"
    };

    private AuthConstant() {

    }
}

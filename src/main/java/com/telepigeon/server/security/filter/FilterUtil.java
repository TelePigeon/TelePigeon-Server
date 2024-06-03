package com.telepigeon.server.security.filter;

import com.telepigeon.server.constant.AuthConstant;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class FilterUtil {

    public static boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return Arrays.stream(AuthConstant.AUTH_WHITELIST).toList().stream().anyMatch(whiteListedURI -> {
            if (whiteListedURI.endsWith("/**")) {
                String basePattern = whiteListedURI.substring(0, whiteListedURI.length() - 3);
                return requestURI.startsWith(basePattern);
            } else {
                return whiteListedURI.equals(requestURI);
            }
        });
    }
}

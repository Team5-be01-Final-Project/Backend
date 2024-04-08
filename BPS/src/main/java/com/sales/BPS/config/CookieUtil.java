package com.sales.BPS.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void createCookie(HttpServletResponse response, String name, String value, int maxAge, boolean isHttpOnly, boolean isSecure, String sameSite) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(isHttpOnly);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setSecure(isSecure);
        // SameSite 설정을 위한 별도의 헤더는 이 메소드 내에서 직접 추가하지 않습니다.
        // 대신, 쿠키를 추가할 때 Set-Cookie 헤더를 별도로 정의해야 할 경우가 있습니다.
        response.addCookie(cookie);

        // SameSite 설정이 필요한 경우, 응답 헤더에 직접 추가합니다.
        if (sameSite != null) {
            String cookieHeader = String.format("%s=%s; Path=/; Max-Age=%d; %s%s; SameSite=%s",
                    name, value, maxAge, isHttpOnly ? "HttpOnly; " : "", isSecure ? "Secure; " : "", sameSite);
            response.addHeader("Set-Cookie", cookieHeader);
        }
    }

    public static void invalidateCookie(HttpServletResponse response, String name) {
        createCookie(response, name, null, 0, false, false, "Lax");
    }


}

package com.app.app.infraestructure.util;

import com.app.app.infraestructure.security.CookieProperties;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieUtil {

    private final CookieProperties cookieProperties;

    public Cookie createAuthCookie(String token) {
        Cookie cookie = new Cookie(cookieProperties.getName(), token);
        cookie.setHttpOnly(cookieProperties.isHttpOnly());
        cookie.setSecure(cookieProperties.isSecure());
        cookie.setMaxAge((int) cookieProperties.getMaxAge());
        cookie.setPath("/");
        cookie.setAttribute("SameSite", cookieProperties.getSameSite());
        return cookie;
    }

    public Cookie clearAuthCookie() {
        Cookie cookie = new Cookie(cookieProperties.getName(), "");
        cookie.setHttpOnly(cookieProperties.isHttpOnly());
        cookie.setSecure(cookieProperties.isSecure());
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setAttribute("SameSite", cookieProperties.getSameSite());
        return cookie;
    }
}


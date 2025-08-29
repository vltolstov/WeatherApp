package org.weather.app.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.weather.app.constant.AuthCookie;

public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(AuthCookie.COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(-1);

        response.addCookie(cookie);
    }

    public static void clearCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(AuthCookie.COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);
    }
}

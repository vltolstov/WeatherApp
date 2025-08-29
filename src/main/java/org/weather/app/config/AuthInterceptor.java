package org.weather.app.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.weather.app.constant.AuthCookie;
import org.weather.app.repository.SessionRepository;
import org.weather.app.service.UserService;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final SessionRepository sessionRepository;
    private final UserService userService;

    public AuthInterceptor(SessionRepository sessionRepository, UserService userService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authToken = "";

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(AuthCookie.COOKIE_NAME)) {
                    authToken = cookie.getValue();
                }
            }
        }

        if (authToken.isEmpty() || authToken == null) {
            response.sendRedirect("login");
            return false;
        }

        //написать условие проверки соответствия куков
        return true;
    }
}

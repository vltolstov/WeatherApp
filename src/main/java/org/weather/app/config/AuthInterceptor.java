package org.weather.app.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.weather.app.constant.AuthCookie;
import org.weather.app.model.Session;
import org.weather.app.repository.SessionRepository;

import java.time.LocalDateTime;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final SessionRepository sessionRepository;

    public AuthInterceptor(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
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

        if (authToken.isEmpty()) {
            response.sendRedirect("login");
            return false;
        }

        Session session = sessionRepository.findById(authToken);
        if (session == null || session.getExpiresAt().isBefore(LocalDateTime.now())) {
            response.sendRedirect("login");
            return false;
        }

        return true;
    }
}

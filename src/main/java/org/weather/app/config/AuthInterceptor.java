package org.weather.app.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
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

        String uri = request.getRequestURI().substring(request.getContextPath().length());
        String authToken = "";

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(AuthCookie.COOKIE_NAME)) {
                    authToken = cookie.getValue();
                    break;
                }
            }
        }

        Session session = null;
        if (!authToken.isEmpty()) {
            session = sessionRepository.findById(authToken).orElse(null);
        }

        boolean isAuthenticated = session != null && session.getExpiresAt().isAfter(LocalDateTime.now());

        if (isAuthenticated && (uri.equals("/login") || uri.equals("/registration"))) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }

        if (!isAuthenticated && !uri.equals("/login") && !uri.equals("/registration")) {
            response.sendRedirect("login");
            return false;
        }

        if (isAuthenticated) {
            request.setAttribute("user", session.getUser());
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Object user = request.getAttribute("user");
            modelAndView.addObject("isAuthenticated", user != null);
            modelAndView.addObject("user", user);
        }
    }
}

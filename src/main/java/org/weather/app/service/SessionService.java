package org.weather.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.weather.app.model.Session;
import org.weather.app.model.User;
import org.weather.app.repository.SessionRepository;
import org.weather.app.util.TokenGenerator;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public String createSession(User user, LocalDateTime expiresAtDate) {

        String token = TokenGenerator.generate();
        Session session = new Session(token, user, expiresAtDate);
        sessionRepository.save(session);

        return token;
    }
}

package org.weather.app.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.weather.app.repository.SessionRepository;

import java.time.LocalDateTime;

@Component
public class SessionCleanupScheduler {

    private final SessionRepository sessionRepository;

    public SessionCleanupScheduler(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Scheduled(fixedRate = 60 * 60 * 1000)
    @Transactional
    public void cleanSessions() {
        sessionRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }
}

package org.weather.app.integration;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.weather.app.config.SpringConfig;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.model.User;
import org.weather.app.repository.SessionRepository;
import org.weather.app.repository.UserRepository;
import org.weather.app.service.AuthService;
import org.weather.app.service.SessionService;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@Transactional
public class SessionServiceIT {

    @Autowired
    Flyway flyway;

    @Autowired
    private AuthService authService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void resetDb() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Успешное создание сессии")
    public void testCreateSession() {
        String userName = "testName";
        String password = "password";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName(userName);
        userRegistrationRequest.setPassword(password);
        authService.registerUser(userRegistrationRequest);

        User user = userRepository.getUserByName(userName);
        String authToken = sessionService.createSession(user, LocalDateTime.now());

        Assertions.assertNotNull(sessionRepository.findById(authToken));
    }

    @Test
    @DisplayName("Удаление истекшей сессии")
    public void testDeleteExpiredSession() {
        String userName = "testName";
        String password = "password";
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName(userName);
        userRegistrationRequest.setPassword(password);
        authService.registerUser(userRegistrationRequest);

        User user = userRepository.getUserByName(userName);
        String authExpiredToken = sessionService.createSession(user, LocalDateTime.now().minusDays(1));
        String authActiveToken = sessionService.createSession(user, LocalDateTime.now().plusDays(1));

        sessionRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        Assertions.assertTrue(sessionRepository.findById(authExpiredToken).isEmpty());
        Assertions.assertTrue(sessionRepository.findById(authActiveToken).isPresent());
    }
}

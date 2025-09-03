package org.weather.app;

import org.flywaydb.core.Flyway;
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
import org.weather.app.repository.UserRepository;
import org.weather.app.service.AuthService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
@Transactional
public class AuthServiceIT {

    @Autowired
    Flyway flyway;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void resetDb() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Проверка регистрации testName - password")
    public void registerUser() {

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName("testName");
        userRegistrationRequest.setPassword("password");

        authService.registerUser(userRegistrationRequest);

        User savedUser = userRepository.getUserByName("testName");

        assertNotNull(savedUser);
        assertEquals("testName", savedUser.getName());
    }
}

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
import org.weather.app.dto.UserLoginRequest;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.exception.InvalidCredentialsException;
import org.weather.app.exception.UsernameAlreadyExistsException;
import org.weather.app.model.Session;
import org.weather.app.model.User;
import org.weather.app.repository.SessionRepository;
import org.weather.app.repository.UserRepository;
import org.weather.app.service.AuthService;

import java.time.LocalDateTime;

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

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void resetDb() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @DisplayName("Регистрация пользователя: имя - testName, пароль - password")
    public void testRegisterUser() {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName("testName");
        userRegistrationRequest.setPassword("password");

        authService.registerUser(userRegistrationRequest);

        User savedUser = userRepository.getUserByName("testName");

        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("testName", savedUser.getName());
    }

    @Test
    @DisplayName("Запрет регистрации двух одинаковых пользователей: имя - testName, пароль password")
    public void testRegisterAlreadyExistsUser() {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName("testName");
        userRegistrationRequest.setPassword("password");

        authService.registerUser(userRegistrationRequest);

        Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> authService.registerUser(userRegistrationRequest));
    }

    @Test
    @DisplayName("Успешный вход пользователя с данными: имя - testName, пароль password")
    public void testLoginUser() {
        String username = "testName";
        String password = "password";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName(username);
        userRegistrationRequest.setPassword(password);

        authService.registerUser(userRegistrationRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setName(username);
        userLoginRequest.setPassword(password);

        String authToken = authService.loginUser(userLoginRequest);
        Session session = sessionRepository.findById(authToken).get();
        Assertions.assertEquals(username, session.getUser().getName());
    }

    @Test
    @DisplayName("Запрет входа по неправильному логину")
    public void testLoginInvalidUserName() {
        String username = "testName";
        String password = "password";
        String invalidUserName = "invalidUserName";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName(username);
        userRegistrationRequest.setPassword(password);

        authService.registerUser(userRegistrationRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setName(invalidUserName);
        userLoginRequest.setPassword(password);

        Assertions.assertThrows(InvalidCredentialsException.class, () -> authService.loginUser(userLoginRequest));
    }

    @Test
    @DisplayName("Запрет входа по неправильному паролю")
    public void testLoginInvalidPassword() {
        String username = "testName";
        String password = "password";
        String invalidPassword = "invalidPassword";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName(username);
        userRegistrationRequest.setPassword(password);
        authService.registerUser(userRegistrationRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setName(username);
        userLoginRequest.setPassword(invalidPassword);

        Assertions.assertThrows(InvalidCredentialsException.class, () -> authService.loginUser(userLoginRequest));
    }

    @Test
    @DisplayName("Создание сессии при входе")
    public void testCreateSession() {
        String userName = "testName";
        String password = "password";

        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName(userName);
        userRegistrationRequest.setPassword(password);
        authService.registerUser(userRegistrationRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setName(userName);
        userLoginRequest.setPassword(password);

        String authToken = authService.loginUser(userLoginRequest);

        Assertions.assertNotNull(sessionRepository.findById(authToken));
    }

    @Test
    @DisplayName("Период сессии при авторизации корректен")
    public void testVerifySessionExpiresDate() {
        String userName = "testName";
        String password = "password";
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
        userRegistrationRequest.setName(userName);
        userRegistrationRequest.setPassword(password);
        authService.registerUser(userRegistrationRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setName(userName);
        userLoginRequest.setPassword(password);
        String authToken = authService.loginUser(userLoginRequest);

        Session session = sessionRepository.findById(authToken).get();

        Assertions.assertTrue(session.getExpiresAt().isAfter(LocalDateTime.now()));
    }
}

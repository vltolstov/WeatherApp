package org.weather.app.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weather.app.dto.UserLoginRequest;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.exception.InvalidCredentialsException;
import org.weather.app.exception.UsernameAlreadyExistsException;
import org.weather.app.model.User;
import org.weather.app.repository.UserRepository;
import org.weather.app.util.MappingUtil;
import org.weather.app.util.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SessionService sessionService;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public void registerUser(UserRegistrationRequest request) {

        if (userRepository.existsByName(request.getName())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        String hashedPassword = PasswordEncoder.hashPassword(request.getPassword());

        User user = MappingUtil.convertToEntity(request);
        user.setPassword(hashedPassword);

        try {
            userRepository.save(user);
            log.info("User registered successfully: userId={}, username={}", user.getId(), user.getName());
        } catch (Exception e) {
            log.error("User registration failed: username={}, error={}", user.getName(), e.getMessage(), e);
            throw e;
        }

    }

    public String loginUser(UserLoginRequest request) {

        User user = userRepository.getUserByName(request.getName());

        if (!userRepository.existsByName(request.getName())) {
            throw new InvalidCredentialsException("Login or password incorrect");
        }
        if (!PasswordEncoder.verifyPassword(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Login or password incorrect");
        }

        return sessionService.createSession(user);
    }

}

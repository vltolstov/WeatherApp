package org.weather.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.model.User;
import org.weather.app.repository.UserRepository;
import org.weather.app.util.MappingUtil;
import org.weather.app.util.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void registerUser(UserRegistrationRequest request) {

        String hashedPassword = PasswordEncoder.hashPassword(request.getPassword());

        User user = MappingUtil.convertToEntity(request);
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    public void loginUser(UserRegistrationRequest request) {

        //авторизация пользователя

        //проверка пользователя в базе

        //проверка пароля

    }

}

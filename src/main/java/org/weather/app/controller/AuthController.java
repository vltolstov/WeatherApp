package org.weather.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "pages/login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("userRegistrationDto") UserRegistrationRequest dto) {

        userService.loginUser(dto);

        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}

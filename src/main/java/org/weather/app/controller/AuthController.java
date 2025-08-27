package org.weather.app.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.weather.app.dto.UserLoginRequest;
import org.weather.app.exception.InvalidCredentialsException;
import org.weather.app.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "pages/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("userLoginRequest") UserLoginRequest request, BindingResult result) {

        if (result.hasErrors()) {
            return "pages/login";
        }

        try {
            userService.loginUser(request);
        } catch (InvalidCredentialsException e) {
            result.reject("login.invalid.message");
            return "pages/login";
        }

        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
}

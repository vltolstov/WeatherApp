package org.weather.app.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.service.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationRequest());
        return "pages/registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@Valid @ModelAttribute("userRegistrationDto") UserRegistrationRequest userRegistrationDto, BindingResult result) {

        if (result.hasErrors()) {
            return "pages/registration";
        }

        userService.registerUser(userRegistrationDto);

        return "redirect:/login";
    }
}

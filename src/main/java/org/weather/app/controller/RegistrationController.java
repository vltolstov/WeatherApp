package org.weather.app.controller;

import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.weather.app.dto.UserRegistrationRequest;
import org.weather.app.exception.UsernameAlreadyExistsException;
import org.weather.app.service.UserService;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userRegistrationRequest", new UserRegistrationRequest());
        return "pages/registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@Valid @ModelAttribute("userRegistrationRequest") UserRegistrationRequest request, BindingResult result) {

        if (result.hasErrors()) {
            return "pages/registration";
        }

        try {
            userService.registerUser(request);
        } catch (UsernameAlreadyExistsException e) {
            result.reject("user.name.exists");
            return "pages/registration";
        }

        return "redirect:/login";
    }
}

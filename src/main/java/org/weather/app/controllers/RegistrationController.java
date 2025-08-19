package org.weather.app.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.weather.app.dto.UserRegistrationDto;

@Controller
public class RegistrationController {

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        return "pages/registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@Valid @ModelAttribute("userRegistrationDto") UserRegistrationDto userRegistrationDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "pages/registration";
        }

        //сохранение пользователя

        return "redirect:/index";
    }
}

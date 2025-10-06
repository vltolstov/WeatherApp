package org.weather.app.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.weather.app.dto.UserLoginRequest;
import org.weather.app.exception.InvalidCredentialsException;
import org.weather.app.service.AuthService;
import org.weather.app.util.CookieUtil;

@Controller
public class AuthController {

    private final AuthService userService;

    public AuthController(AuthService authService) {
        this.userService = authService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "pages/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid @ModelAttribute("userLoginRequest") UserLoginRequest loginRequest,
                               BindingResult result,
                               Model model,
                               HttpServletResponse response) {

        if (result.hasErrors()) {
            return "pages/login";
        }

        try {
            String authToken = userService.loginUser(loginRequest);
            CookieUtil.addCookie(response, authToken);
            return "redirect:/";
        } catch (InvalidCredentialsException e) {
            result.reject("login.invalid.message");
            return "pages/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        CookieUtil.clearCookie(response);
        return "redirect:/login";
    }
}

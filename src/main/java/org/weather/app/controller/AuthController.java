package org.weather.app.controller;

import jakarta.servlet.http.HttpServletRequest;
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

import java.io.IOException;

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
    public void processLogin(@Valid @ModelAttribute("userLoginRequest") UserLoginRequest loginRequest,
                             BindingResult result,
                             HttpServletRequest request,
                             HttpServletResponse response) throws IOException {

        if (result.hasErrors()) {
            response.sendRedirect(request.getContextPath() + "/login");
        }

        String authToken = "";
        try {
            authToken = userService.loginUser(loginRequest);
        } catch (InvalidCredentialsException e) {
            result.reject("login.invalid.message");
            response.sendRedirect(request.getContextPath() + "/login");
        }

        CookieUtil.addCookie(response, authToken);
        response.sendRedirect(request.getContextPath() + "/");
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        CookieUtil.clearCookie(response);
        return "redirect:/login";
    }
}

package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.dto.LoginUserFormData;
import com.pustovalov.weatherapplication.entity.Session;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.service.SessionService;
import com.pustovalov.weatherapplication.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequiredArgsConstructor

@Controller
@RequestMapping("/login")
public class AuthenticationController {

    public static final String SESSION_COOKIE_NAME = "SESSIONID";

    private final UserService userService;

    private final SessionService sessionService;

    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    @PostMapping
    public String verifyCredentials(@NotNull LoginUserFormData formData, HttpServletResponse response, Model model) {
        Optional<User> optionalUser = userService.findBy(formData.login());
        if (userService.isValidUserCredentials(formData, optionalUser)) {
            Session session = sessionService.save(optionalUser.get());
            Cookie sessionId = new Cookie(SESSION_COOKIE_NAME, session.getId().toString());
            sessionId.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(sessionId);
            return "redirect:/weather";
        } else {
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login";
        }
    }

}

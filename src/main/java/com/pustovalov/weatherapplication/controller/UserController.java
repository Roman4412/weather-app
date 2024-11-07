package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.dto.CreateUserFormData;
import com.pustovalov.weatherapplication.dto.LoginUserFormData;
import com.pustovalov.weatherapplication.entity.Session;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import com.pustovalov.weatherapplication.service.SessionService;
import com.pustovalov.weatherapplication.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {

    public static final String SESSION_COOKIE_NAME = "SESSIONID";

    private final UserService userService;

    private final SessionService sessionService;

    @GetMapping("/registration")
    public String getRegistrationPage(Model model, CreateUserFormData createUserFormData) {
        model.addAttribute("createUserFormData", createUserFormData);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid CreateUserFormData createUserFormData, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.save(createUserFormData);
        }
        return "redirect:/users/login";
    }

    @PostMapping("/logout")
    public String logout(@RequestAttribute @NotNull String sessionId) {
        sessionService.delete(UUID.fromString(sessionId));
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@NotNull LoginUserFormData formData,
                        HttpServletResponse response,
                        Model model,
                        @Value("${cookie.maxAge}") Integer maxAge) {

        Optional<User> optionalUser = userService.findBy(formData.login());
        if (optionalUser.isEmpty()) {
            return "forward:/users/login";
        }

        User user = optionalUser.get();
        if (!userService.isValidUserCredentials(formData, user)) {
            model.addAttribute("errorMessage", "Invalid username or password");
            return "forward:/users/login";
        }

        Session session = sessionService.save(user);
        Cookie sessionId = new Cookie(SESSION_COOKIE_NAME, session.getId().toString());
        sessionId.setMaxAge(maxAge);
        sessionId.setPath("/");
        response.addCookie(sessionId);

        return "redirect:/locations/weather";
    }

    @ExceptionHandler(ObjectAlreadyExistException.class)
    public String handleObjectAlreadyExistException(ObjectAlreadyExistException ex, Model model) {
        model.addAttribute("createUserFormData", new CreateUserFormData());
        model.addAttribute("error", ex.getMessage());
        return "registration";
    }
}

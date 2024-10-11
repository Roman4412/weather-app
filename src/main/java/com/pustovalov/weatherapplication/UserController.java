package com.pustovalov.weatherapplication;

import com.pustovalov.weatherapplication.dto.CreateUserDto;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import com.pustovalov.weatherapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor

@Controller
@RequestMapping("/weather")
public class UserController {

    private final UserService userService;

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(CreateUserDto createUserDto) {

        // валидация данных поля на null , login

        try {
            userService.create(createUserDto);
            return "login";
        } catch (ObjectAlreadyExistException e) {
            // вывести message
            return "registration";
        }

    }


}

package com.pustovalov.weatherapplication;

import com.pustovalov.weatherapplication.dto.CreateUserFormData;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import com.pustovalov.weatherapplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor

@Controller
@RequestMapping
public class UserController {

    private final UserService userService;

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid CreateUserFormData createUserFormData, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList();
            model.addAttribute("createUserFormData", createUserFormData);
            model.addAttribute("errors", errors);
            return "registration";
        }

        try {
            userService.create(createUserFormData);
            return "redirect:/login";
        } catch (ObjectAlreadyExistException e) {
            // вывести message
            return "registration";
        }

    }


}

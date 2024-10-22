package com.pustovalov.weatherapplication.controller;

import com.pustovalov.weatherapplication.dto.CreateUserFormData;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import com.pustovalov.weatherapplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String showRegistrationPage(Model model, CreateUserFormData createUserFormData) {
        model.addAttribute("createUserFormData", createUserFormData);
        return "registration";
    }

    @PostMapping
    public String registerUser(@Valid CreateUserFormData createUserFormData, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            userService.save(createUserFormData);
            return "redirect:/login";
        } catch (ObjectAlreadyExistException e) {
            model.addAttribute("error", e);
            return "registration";
        }

    }

}

package com.pustovalov.weatherapplication.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserFormData(
        @NotNull(message ="{user.registration.login}")
        @Size(min = 1 , max = 20, message = "{user.registration.login.length}")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{user.registration.login.format}")
        String login,
        @NotNull(message = "{user.registration.password}")
        @Size(min = 8 , message = "{user.registration.password.length}")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{user.registration.password.format}")
        String password
) {

}
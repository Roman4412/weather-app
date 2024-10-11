package com.pustovalov.weatherapplication.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserFormData(
        @NotNull(message = "The login field must be filled in")
        @Size(min = 1 , max = 20, message = "The length of the login must be no more than 20 and no less than 1 character")
        String login,
        @NotNull(message = "The password field must be filled in")
        @Size(min = 6 , message = "The password must be at least 8 characters long")
        String password
) {

}

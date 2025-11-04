package com.me.cinemaapp.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UserSignUpRequest(@NotEmpty(message = "Username is required")
                                @Size(max = 30, message = "Username can not be longer than 30 characters")
                                String username,
                                @NotEmpty(message = "Name is required")
                                @Size(max = 30, message = "Name can not be longer than 30 characters")
                                String name,
                                @Size(max = 30, message = "Surname can not be longer than 30 characters")
                                @NotEmpty(message = "Surname is required")
                                String surname,
                                @Size(max = 30, message = "Patronymic can not be longer than 30 characters")
                                @NotEmpty(message = "Patronymic is required")
                                String patronymic,
                                @Size(min = 8, max = 30, message = "Password  can not be lower than 8 and greater than 30 characters")
                                @NotEmpty(message = "Password is required") String password) {
}

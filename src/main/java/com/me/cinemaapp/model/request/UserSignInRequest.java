package com.me.cinemaapp.model.request;

import jakarta.validation.constraints.NotEmpty;

public record UserSignInRequest(@NotEmpty(message = "Username is required")
                                String username,
                                @NotEmpty(message = "Password is required")
                                String password) {
}

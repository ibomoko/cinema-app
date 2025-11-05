package com.me.cinemaapp.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MovieRequest(@NotEmpty(message = "Title is required")
                              String title,
                           @NotEmpty(message = "Description is required")
                              String description,
                           @NotNull(message = "Duration is required")
                              Integer duration,
                           @NotEmpty(message = "Genre is required")
                              String genre,
                           @NotNull(message = "Release year is required")
                              Integer releaseYear) {
}

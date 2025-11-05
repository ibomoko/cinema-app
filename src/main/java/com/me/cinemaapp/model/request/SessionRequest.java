package com.me.cinemaapp.model.request;

import com.me.cinemaapp.enums.SessionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SessionRequest(@NotNull(message = "Hall id is required")
                             String hallId,
                             @NotNull(message = "Movie id is required")
                             String movieId,
                             @NotNull(message = "Price is required")
                             @DecimalMin(value = "5.0")
                             BigDecimal price,
                             @NotNull(message = "Session type is required")
                             SessionType type,
                             @NotNull(message = "Date for the session is required")
                             Long startDate) {
}

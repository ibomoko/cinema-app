package com.me.cinemaapp.model.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TicketBuyRequest(@NotEmpty(message = "Session id is required")
                               String sessionId,
                               @NotEmpty(message = "At least one seat id is required" )
                               List<String> seatIds) {
}

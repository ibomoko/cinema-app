package com.me.cinemaapp.model.response;

import com.me.cinemaapp.enums.SessionType;

import java.math.BigDecimal;

public record SessionResponse(String id, Long startDate, SessionType type, BigDecimal price, String hallId) {
}

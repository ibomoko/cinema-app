package com.me.cinemaapp.model.request;

import com.me.cinemaapp.enums.SessionType;

import java.math.BigDecimal;

public record SessionFilter(String hallName,
                            String movieTitle,
                            BigDecimal priceFrom,
                            BigDecimal priceTo,
                            SessionType sessionType) {
}

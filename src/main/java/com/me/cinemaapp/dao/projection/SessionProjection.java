package com.me.cinemaapp.dao.projection;

import com.me.cinemaapp.enums.SessionType;

import java.math.BigDecimal;
import java.util.Date;

public interface SessionProjection {
    String getId();
    Date getStartDate();
    SessionType getType();
    BigDecimal getPrice();
    String getHallId();
    String getMovieId();
}

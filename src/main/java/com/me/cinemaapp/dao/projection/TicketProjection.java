package com.me.cinemaapp.dao.projection;

import com.me.cinemaapp.enums.SessionType;
import com.me.cinemaapp.enums.TicketStatus;

import java.util.Date;

public interface TicketProjection {
    String getId();
    String getMovieTitle();
    Date getPurchaseDate();
    Date getStartDate();
    TicketStatus getStatus();
    SessionType getSessionType();
    Integer getRowNumber();
    Integer getSeatNumber();
    Boolean getIsExpired();
    String getHallName();
}

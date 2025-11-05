package com.me.cinemaapp.enums;

import lombok.Getter;

@Getter
public enum SessionType {
    MORNING(11),
    AFTERNOON(15),
    EVENING(19),
    NIGHT(23);

    private final int hour;

    SessionType(int hour) {
        this.hour = hour;
    }

}

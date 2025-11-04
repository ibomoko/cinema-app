package com.me.cinemaapp.util;

import java.time.Instant;
import java.util.Date;

public class DateHelper {

    // Using this to get dates from one place, it is easy to change later if needed.
    public static Date getUtcNow() {
        return Date.from(Instant.now());
    }
}

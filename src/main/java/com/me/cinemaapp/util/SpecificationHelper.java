package com.me.cinemaapp.util;

import org.apache.commons.lang3.StringUtils;

public class SpecificationHelper {
    public static String prepareSearchText(String text) {
        if (StringUtils.isBlank(text)) {
            return "%";
        }
        return "%" + text.trim().replace(" ", "%").toLowerCase() + "%";
    }
}
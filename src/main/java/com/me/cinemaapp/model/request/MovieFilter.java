package com.me.cinemaapp.model.request;


public record MovieFilter(String title,
                          String description,
                          Integer durationFrom,
                          Integer durationTo,
                          String genre,
                          Integer releaseYearFrom,
                          Integer releaseYearTo) {

}

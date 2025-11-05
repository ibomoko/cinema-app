package com.me.cinemaapp.model;

public record MovieResponse(String id,
                            String title,
                            String description,
                            String genre,
                            Integer duration,
                            Integer releaseYear) {
}

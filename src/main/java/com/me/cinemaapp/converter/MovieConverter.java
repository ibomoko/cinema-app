package com.me.cinemaapp.converter;

import com.me.cinemaapp.entity.Movie;
import com.me.cinemaapp.model.request.MovieRequest;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MovieConverter implements Function<MovieRequest, Movie> {
    @Override
    public Movie apply(MovieRequest request) {
        return Movie.builder()
                .title(request.title())
                .description(request.description())
                .duration(request.duration())
                .genre(request.genre())
                .releaseYear(request.releaseYear())
                .build();
    }
}

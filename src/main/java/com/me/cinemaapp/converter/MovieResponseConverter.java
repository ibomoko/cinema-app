package com.me.cinemaapp.converter;

import com.me.cinemaapp.entity.Movie;
import com.me.cinemaapp.model.MovieResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MovieResponseConverter implements Function<Movie, MovieResponse> {
    @Override
    public MovieResponse apply(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getGenre(),
                movie.getDuration(),
                movie.getReleaseYear()
        );
    }
}

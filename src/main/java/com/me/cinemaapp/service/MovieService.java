package com.me.cinemaapp.service;

import com.me.cinemaapp.converter.MovieConverter;
import com.me.cinemaapp.converter.MovieResponseConverter;
import com.me.cinemaapp.converter.SessionResponseConverter;
import com.me.cinemaapp.dao.MovieRepository;
import com.me.cinemaapp.dao.SessionRepository;
import com.me.cinemaapp.dao.projection.SessionProjection;
import com.me.cinemaapp.entity.Movie;
import com.me.cinemaapp.entity.Session;
import com.me.cinemaapp.error.exception.ResourceAlreadyExistException;
import com.me.cinemaapp.error.exception.ResourceNotFoundException;
import com.me.cinemaapp.model.response.MovieResponse;
import com.me.cinemaapp.model.request.MovieFilter;
import com.me.cinemaapp.model.request.MovieRequest;
import com.me.cinemaapp.model.response.SessionResponse;
import com.me.cinemaapp.specification.MovieSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final SessionRepository sessionRepository;
    private final MovieConverter movieConverter;
    private final MovieResponseConverter movieResponseConverter;
    private final SessionResponseConverter sessionResponseConverter;


    public void addMovie(@Valid MovieRequest request) {
        if (movieRepository.existsByTitle(request.title())) {
            throw new ResourceAlreadyExistException("Movie already exists with this title.");
        }
        movieRepository.save(movieConverter.apply(request));
    }

    public void updateMovie(String id, @Valid MovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Movie not found with this id."));

        movie.setTitle(request.title());
        movie.setDescription(request.description());
        movie.setDuration(request.duration());
        movie.setGenre(request.genre());
        movie.setReleaseYear(request.releaseYear());

        movieRepository.save(movie);
    }

    public Page<MovieResponse> getMovies(MovieFilter movieFilter, Pageable pageable) {
        MovieSpecification movieSpecification = new MovieSpecification(movieFilter);
        return movieRepository.findAll(movieSpecification, pageable).map(movieResponseConverter);
    }

    public MovieResponse getMovieById(String id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with this id."));

        return movieResponseConverter.apply(movie);
    }

    public Page<SessionResponse> getMovieSessions(String id, Pageable pageable) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Movie not found with this id."));

        Page<Session> sessions = sessionRepository.findByMovieIdAndIsClosed(movie.getId(), false, pageable);
        return sessions.map(sessionResponseConverter);
    }
}

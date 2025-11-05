package com.me.cinemaapp.controller;

import com.me.cinemaapp.model.response.MovieResponse;
import com.me.cinemaapp.model.request.MovieFilter;
import com.me.cinemaapp.model.request.MovieRequest;
import com.me.cinemaapp.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpStatus addMovie(@RequestBody @Valid MovieRequest request) {
        movieService.addMovie(request);
        return HttpStatus.CREATED;
    }

    // Frontend must send all properties. Unchanged fields should use current values from GET API.
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpStatus updateMovie(@PathVariable String id, @RequestBody @Valid MovieRequest request) {
        movieService.updateMovie(id, request);
        return HttpStatus.OK;
    }

    @GetMapping
    public Page<MovieResponse> getMovies(@ParameterObject MovieFilter movieFilter,
                                         @ParameterObject Pageable pageable) {
        return movieService.getMovies(movieFilter, pageable);
    }

    @GetMapping("/{id}")
    public MovieResponse getMovie(@PathVariable String id) {
        return movieService.getMovieById(id);
    }

}

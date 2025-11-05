package com.me.cinemaapp.service;

import com.me.cinemaapp.dao.HallRepository;
import com.me.cinemaapp.dao.MovieRepository;
import com.me.cinemaapp.dao.SessionRepository;
import com.me.cinemaapp.dao.projection.SessionProjection;
import com.me.cinemaapp.entity.Hall;
import com.me.cinemaapp.entity.Movie;
import com.me.cinemaapp.entity.Session;
import com.me.cinemaapp.error.exception.ResourceAlreadyExistException;
import com.me.cinemaapp.error.exception.ResourceNotFoundException;
import com.me.cinemaapp.model.request.SessionFilter;
import com.me.cinemaapp.model.request.SessionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final HallRepository hallRepository;
    private final MovieRepository movieRepository;

    public void addSession(SessionRequest request) {
        Movie movie = movieRepository.findById(request.movieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with this id: " + request.movieId()));

        Hall hall = hallRepository.findById(request.hallId())
                .orElseThrow(() -> new ResourceNotFoundException("Hall not found with this id: " + request.hallId()));

        if (sessionRepository.existsByStartDateAndType(new Date(request.startDate()), request.type())) {
            throw new ResourceAlreadyExistException("Session already exists at this date");
        }

        Session session = Session.builder()
                .startDate(new Date(request.startDate()))
                .type(request.type())
                .price(request.price())
                .hall(hall)
                .movie(movie)
                .build();

        sessionRepository.save(session);
    }

    public Page<SessionProjection> getSessionPage(SessionFilter filter, Pageable pageable) {
        // Used this query because we can't use EntityGraph and Specification together
        return sessionRepository.findByHallNameAndMovieTitleAndSessionPriceAndSessionType(filter.hallName(),
                filter.movieTitle(),
                filter.priceFrom(),
                filter.priceTo(),
                filter.sessionType(),
                pageable
        );
    }

}

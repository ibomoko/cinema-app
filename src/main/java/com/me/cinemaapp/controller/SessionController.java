package com.me.cinemaapp.controller;

import com.me.cinemaapp.dao.projection.SessionProjection;
import com.me.cinemaapp.model.request.SessionFilter;
import com.me.cinemaapp.model.request.SessionRequest;
import com.me.cinemaapp.service.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpStatus createSession(@Valid @RequestBody SessionRequest request) {
        sessionService.addSession(request);
        return HttpStatus.CREATED;
    }

    @GetMapping
    public Page<SessionProjection> getSessions(@ParameterObject SessionFilter filter,
                                               @ParameterObject Pageable pageable) {
        return sessionService.getSessionPage(filter, pageable);
    }

}

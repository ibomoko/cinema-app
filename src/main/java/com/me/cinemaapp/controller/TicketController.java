package com.me.cinemaapp.controller;

import com.me.cinemaapp.dao.projection.TicketProjection;
import com.me.cinemaapp.model.request.TicketBuyRequest;
import com.me.cinemaapp.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    // If needed we can use Specification to support other filter parameters
    @GetMapping
    public Page<TicketProjection> getTickets(@RequestParam Boolean isExpired,
                                             @ParameterObject Pageable pageable) {
        return ticketService.getTickets(isExpired, pageable);
    }

    @GetMapping("/{id}")
    public TicketProjection getTicket(@PathVariable String id) {
        return ticketService.getTicketById(id);
    }

    @PostMapping
    public HttpStatus buyTicket(@RequestBody @Valid TicketBuyRequest request) {
        ticketService.buyTicket(request);
        return HttpStatus.OK;
    }

    @PutMapping("/{id}")
    public HttpStatus refundTicket(@PathVariable String id) {
        ticketService.refundTicket(id);
        return HttpStatus.OK;
    }

}

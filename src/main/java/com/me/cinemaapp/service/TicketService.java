package com.me.cinemaapp.service;

import com.me.cinemaapp.dao.SeatRepository;
import com.me.cinemaapp.dao.SessionRepository;
import com.me.cinemaapp.dao.TicketRepository;
import com.me.cinemaapp.dao.UserRepository;
import com.me.cinemaapp.dao.projection.TicketProjection;
import com.me.cinemaapp.entity.Seat;
import com.me.cinemaapp.entity.Session;
import com.me.cinemaapp.entity.Ticket;
import com.me.cinemaapp.entity.User;
import com.me.cinemaapp.enums.TicketStatus;
import com.me.cinemaapp.error.exception.InsufficientBalanceException;
import com.me.cinemaapp.error.exception.ResourceAlreadyExistException;
import com.me.cinemaapp.error.exception.ResourceNotFoundException;
import com.me.cinemaapp.model.UserInfo;
import com.me.cinemaapp.model.request.TicketBuyRequest;
import com.me.cinemaapp.util.DateHelper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;

    @Resource(name = "requestScopedUser")
    private UserInfo userInfo;


    public void buyTicket(TicketBuyRequest request) {
        User currentUser = userInfo.getUser();
        Session session = sessionRepository.findByIdAndIsClosedFalse(request.sessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Session not found with this id: " + request.sessionId()));

        BigDecimal totalAmount = session.getPrice().multiply(new BigDecimal(request.seatIds().size()));
        if (currentUser.getBalance().compareTo(totalAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to buy tickets for this session.");
        }

        List<Seat> seats = getAvailableSeats(request, session);
        checkExistingTickets(request, session);
        chargeUser(currentUser, totalAmount);

        List<Ticket> newTickets = buildTickets(session, seats, currentUser);
        ticketRepository.saveAll(newTickets);
    }

    private void chargeUser(User currentUser, BigDecimal totalAmount) {
        currentUser.decreaseBalance(totalAmount);
        userRepository.save(currentUser);
    }

    private List<Seat> getAvailableSeats(TicketBuyRequest request, Session session) {
        List<Seat> seats = seatRepository.findByHallIdAndIdIn(session.getHallId(), request.seatIds());
        if (seats.isEmpty() || seats.size() != request.seatIds().size()) {
            throw new ResourceNotFoundException("Seats not founds with the given ids in this hall.");
        }
        return seats;
    }

    private void checkExistingTickets(TicketBuyRequest request, Session session) {
        List<Ticket> tickets = ticketRepository
                .findByIsExpiredFalseAndSessionIdAndSeatIdInAndStatus(session.getId(), request.seatIds(), TicketStatus.PURCHASED);

        if (!tickets.isEmpty()) {
            throw new ResourceAlreadyExistException("Some of the selected seats are already booked.");
        }
    }

    private List<Ticket> buildTickets(Session session, List<Seat> seats, User user) {
        Date currentDate = DateHelper.getUtcNow();
        return seats.stream().map(seat -> Ticket.builder()
                .status(TicketStatus.PURCHASED)
                .purchaseDate(currentDate)
                .user(user)
                .session(session)
                .seat(seat)
                .build())
                .toList();
    }

    public void refundTicket(String id) {
        User currentUser = userInfo.getUser();
        Ticket ticket = ticketRepository
                .findByIdAndUserIdAndStatusAndIsExpiredFalse(id, currentUser.getId(), TicketStatus.PURCHASED)
                .orElseThrow(() -> new ResourceNotFoundException("Purchased ticket not found with id: " + id));

        refundUser(currentUser, ticket);
        ticket.setStatus(TicketStatus.REFUNDED);
        ticketRepository.save(ticket);
    }

    private void refundUser(User currentUser, Ticket ticket) {
        currentUser.increaseBalance(ticket.getSession().getPrice());
        userRepository.save(currentUser);
    }

    public TicketProjection getTicketById(String id) {
        return ticketRepository.findByIdAndUserId(id, userInfo.getUser().getId());
    }

    public Page<TicketProjection> getTickets(Boolean isExpired, Pageable pageable) {
        return ticketRepository.findByUserIdAndIsExpired(userInfo.getUser().getId(), isExpired, pageable);
    }
}

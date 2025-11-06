package com.me.cinemaapp.dao;

import com.me.cinemaapp.dao.projection.TicketProjection;
import com.me.cinemaapp.entity.Ticket;
import com.me.cinemaapp.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

    @EntityGraph(value = "ticket.sessions")
    List<Ticket> findTop100ByStatusAndIsExpiredFalseOrderByPurchaseDateAsc(TicketStatus status);

    @EntityGraph(attributePaths = {"seat"})
    List<Ticket> findByIsExpiredFalseAndSessionIdAndSeatIdInAndStatus(String sessionId, List<String> seatIds, TicketStatus status);

    @EntityGraph(value = "ticket.sessions")
    Optional<Ticket> findByIdAndUserIdAndStatusAndIsExpiredFalse(String id, String userId, TicketStatus status);

    // Used JOIN for Hall and Movie because Hibernate can't fetch
    @Query("""
        SELECT t.id as id,
        t.purchaseDate as purchaseDate,
        t.status as status,
        t.isExpired as isExpired,
        t.session.startDate as startDate,
        t.session.type as sessionType,
        t.seat.rowNumber as rowNumber,
        t.seat.seatNumber as seatNumber,
        h.name as hallName,
        m.title as movieTitle
        FROM Ticket t
        JOIN Hall h ON h.id = t.seat.hall.id
        JOIN Movie m ON m.id = t.session.movie.id
        where
        t.id = :id
        AND t.user.id = :userId

    """)
    TicketProjection findByIdAndUserId(String id, String userId);

    @Query("""
        SELECT t.id as id,
        t.purchaseDate as purchaseDate,
        t.status as status,
        t.isExpired as isExpired,
        t.session.startDate as startDate,
        t.session.type as sessionType,
        t.seat.rowNumber as rowNumber,
        t.seat.seatNumber as seatNumber,
        h.name as hallName,
        m.title as movieTitle
        FROM Ticket t
        JOIN Hall h ON h.id = t.seat.hall.id
        JOIN Movie m ON m.id = t.session.movie.id
        where
        t.user.id = :userId
        AND t.isExpired = :isExpired
        ORDER BY t.purchaseDate DESC
    """)
    Page<TicketProjection> findByUserIdAndIsExpired(String userId, Boolean isExpired, Pageable pageable);
}

package com.me.cinemaapp.dao;

import com.me.cinemaapp.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {

    @Query("""
            SELECT s
            FROM Seat s
            WHERE s.hallId = :hallId
            AND s.id NOT IN (
                   SELECT t.seat.id
                   FROM Ticket t
                   WHERE t.sessionId = :sessionId
                   AND t.isExpired = false
                   AND t.status = 'PURCHASED'
                  )
            ORDER BY s.rowNumber, s.seatNumber
            """)
    List<Seat> findAvailableSeatsBySessionId(String sessionId, String hallId);

    List<Seat> findByHallIdAndIdIn(String hallId, List<String> ids);
}

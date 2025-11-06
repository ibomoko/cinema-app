package com.me.cinemaapp.dao;

import com.me.cinemaapp.dao.projection.SessionProjection;
import com.me.cinemaapp.entity.Session;
import com.me.cinemaapp.enums.SessionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {

    boolean existsByStartDateAndTypeAndHallIdAndIsClosed(Date startDate, SessionType type, String hallId, boolean isClosed);

    @Query("""
            SELECT s.id as id,
            s.startDate as startDate,
            s.type as type,
            s.price as price,
            h.id as hallId,
            m.id as movieId
            FROM Session s
            INNER JOIN Hall h ON h.id = s.hall.id
            INNER JOIN Movie m ON m.id = s.movie.id
            WHERE (:hallName IS NULL OR LOWER(h.name) LIKE LOWER(CONCAT('%', :hallName, '%')))
            AND (:movieTitle IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :movieTitle, '%')))
            AND (:priceFrom IS NULL OR s.price >= :priceFrom)
            AND (:priceTo IS NULL OR s.price <= :priceTo)
            AND (:type IS NULL OR s.type = :type)
            """)
    Page<SessionProjection> findByHallNameAndMovieTitleAndSessionPriceAndSessionType(String hallName,
                                                                                     String movieTitle,
                                                                                     BigDecimal priceFrom,
                                                                                     BigDecimal priceTo,
                                                                                     SessionType type,
                                                                                     Pageable pageable);

    @Modifying
    @Query("""
                UPDATE Session s
                SET s.isClosed = true
                WHERE s.isClosed = false
                  AND s.startDate < :currentDate
            """)
    void closeExpiredSessions(Date currentDate);
    Page<Session> findByMovieIdAndIsClosed(String movieId, boolean isClosed, Pageable pageable);
    Optional<Session> findByIdAndIsClosedFalse(String id);
}

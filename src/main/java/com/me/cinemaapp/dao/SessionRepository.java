package com.me.cinemaapp.dao;

import com.me.cinemaapp.dao.projection.SessionProjection;
import com.me.cinemaapp.entity.Session;
import com.me.cinemaapp.enums.SessionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {

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

    boolean existsByStartDateAndType(Date startDate, SessionType type);
}

package com.me.cinemaapp.entity;

import com.me.cinemaapp.entity.idgenerator.CustomUUID;
import com.me.cinemaapp.enums.SessionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "sessions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Session {
    @Id
    @CustomUUID
    private String id;

    @Column(name = "start_date")
    private Date startDate;

    @Enumerated(EnumType.STRING)
    private SessionType type;

    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id")
    private Hall hall;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

}

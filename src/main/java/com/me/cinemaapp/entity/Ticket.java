package com.me.cinemaapp.entity;

import com.me.cinemaapp.entity.idgenerator.CustomUUID;
import com.me.cinemaapp.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "tickets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ticket {

    @Id
    @CustomUUID
    private String id;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private Session session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

}

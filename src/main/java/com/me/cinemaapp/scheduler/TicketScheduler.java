package com.me.cinemaapp.scheduler;

import com.me.cinemaapp.dao.TicketRepository;
import com.me.cinemaapp.entity.Session;
import com.me.cinemaapp.entity.Ticket;
import com.me.cinemaapp.enums.TicketStatus;
import com.me.cinemaapp.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TicketScheduler {

    private final TicketRepository ticketRepository;

    @Scheduled(fixedRate = 120_000) // 2 minutes
    public void expireOldTickets() {
        List<Ticket> tickets = ticketRepository
                .findTop100ByStatusAndIsExpiredFalseOrderByPurchaseDateAsc(TicketStatus.PURCHASED);

        if (tickets.isEmpty())
            return;

        Date currentDate = DateHelper.getUtcNow();
        tickets.forEach(ticket -> {
            if (isSessionExpired(ticket.getSession(), currentDate)) {
                ticket.setIsExpired(true);
            }
        });

        ticketRepository.saveAll(tickets);
    }

    private boolean isSessionExpired(Session session, Date currentDate) {
        return session.getStartDate().before(currentDate);
    }
}

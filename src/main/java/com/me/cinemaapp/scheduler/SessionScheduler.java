package com.me.cinemaapp.scheduler;

import com.me.cinemaapp.dao.SessionRepository;
import com.me.cinemaapp.util.DateHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class SessionScheduler {

    private final SessionRepository sessionRepository;

    // Session table has few records so updating all records is acceptable.
    @Scheduled(fixedRate = 180_000) // 3 minutes
    @Transactional
    public void closeExpiredSessions() {
        Date currentDate = DateHelper.getUtcNow();
        sessionRepository.closeExpiredSessions(currentDate);
    }
}

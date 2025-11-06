package com.me.cinemaapp.converter;

import com.me.cinemaapp.entity.Session;
import com.me.cinemaapp.model.response.SessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class SessionResponseConverter implements Function<Session, SessionResponse> {
    @Override
    public SessionResponse apply(Session session) {
        return new SessionResponse(
                session.getId(),
                session.getStartDate().getTime(),
                session.getType(),
                session.getPrice(),
                session.getHallId()
        );
    }
}

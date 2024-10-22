package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.entity.Session;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.exception.ObjectNotFoundException;
import com.pustovalov.weatherapplication.repository.ISessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@RequiredArgsConstructor

@Service
public class SessionService {

    private final ISessionRepository repository;

    @Value("${user-session.long.amount}")
    Long amount;

    @Value("${user-session.long.units}")
    String units;

    public Session save(User user) {
        Session session = new Session();
        session.setUser(user);
        session.setExpiresAt(LocalDateTime.now()
                                          .plus(Duration.of(amount, ChronoUnit.valueOf(units))));

        return repository.save(session);
    }

    public Session findBy(UUID sessionId) {
        return repository.findBy(sessionId)
                         .orElseThrow(
                                 () -> new ObjectNotFoundException("session with id %s not found".formatted(sessionId)));
    }

    public void delete(UUID sessionId) {
        repository.delete(sessionId);
    }
}

package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.entity.Session;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.repository.ISessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SessionService {

    //TODO удаление устаревших сессий из бд по расписанию
    private final ISessionRepository repository;

    @Value("${user-session.long.amount}")
    Long amount;

    @Value("${user-session.long.units}")
    String units;

    public Session save(User user) {
        Session session = new Session();
        session.setUser(user);
        Duration duration = Duration.of(amount, ChronoUnit.valueOf(units));
        session.setExpiresAt(LocalDateTime.now().plus(duration));

        return repository.save(session);
    }

    public Optional<Session> findBy(UUID sessionId) {
        return repository.findBy(sessionId);
    }

    public void delete(UUID sessionId) {
        repository.delete(sessionId);
    }

    public boolean isValid(Session session) {
        return session.getExpiresAt().isAfter(LocalDateTime.now());
    }

}

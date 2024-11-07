package com.pustovalov.weatherapplication.repository;

import com.pustovalov.weatherapplication.entity.Session;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ISessionRepository {
    Session save(Session session);

    Optional<Session> findBy(UUID sessionId);

    void delete(UUID sessionId);

    int deleteAllByExpiresAtBefore(LocalDateTime expiresAt);
}

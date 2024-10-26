package com.pustovalov.weatherapplication.repository;

import com.pustovalov.weatherapplication.entity.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SessionRepository extends AbstractSessionTransactionManager implements ISessionRepository {

    public SessionRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Session save(Session newSession) {
        return executeInTransaction(s -> {
            s.persist(newSession);
            return newSession;
        });
    }

    @Override
    public Optional<Session> findBy(UUID sessionId) {
        return executeInTransaction(s -> {
            String query = "SELECT s FROM Session s WHERE s.id = :id";
            return s.createQuery(query, Session.class)
                    .setParameter("id", sessionId)
                    .uniqueResultOptional();
        });
    }

    @Override
    public void delete(UUID sessionId) {
        sessionFactory.inTransaction(s -> {
            String query = "DELETE FROM Session WHERE id = :sessionId";
            s.createQuery(query)
                .setParameter("sessionId", sessionId)
                .executeUpdate();
        });
    }

}

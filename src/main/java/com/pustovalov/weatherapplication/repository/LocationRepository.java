package com.pustovalov.weatherapplication.repository;

import com.pustovalov.weatherapplication.entity.Location;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import org.hibernate.SessionFactory;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationRepository extends AbstractSessionTransactionManager implements ILocationRepository {

    private static final String UNIQUE_VIOLATION_CODE = "23505";

    public LocationRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Location save(Location location) {
        return executeInTransaction(s -> {
            try {
                s.persist(location);
            } catch (Exception e) {
                Throwable cause = e.getCause();
                if (cause instanceof PSQLException psqlException) {
                    if (UNIQUE_VIOLATION_CODE.equals(psqlException.getSQLState())) {
                        throw new ObjectAlreadyExistException(
                                "The location %s already exists for %s".formatted(location.getName(),
                                                                                  location.getUser().getLogin()),
                                cause);
                    }
                } else {
                    throw e;
                }
            }
            return location;
        });
    }

    @Override
    public List<Location> getAll(Long userId) {
        return executeInTransaction(s -> {
            String query = "SELECT Location FROM Location WHERE Location.user.id = :userId";
            return s.createQuery(query, Location.class)
                    .setParameter("userId", userId)
                    .list();
        });
    }

    @Override
    public void delete(Long id) {
        sessionFactory.inTransaction(s -> {
            String query = "DELETE FROM Location WHERE Location.id = :id";
            s.createQuery(query).setParameter("id", id);
        });
    }
}

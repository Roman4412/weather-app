package com.pustovalov.weatherapplication.dao;

import com.pustovalov.weatherapplication.entity.Location;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import org.hibernate.SessionFactory;
import org.postgresql.util.PSQLException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationDao extends AbstractSessionTransactionManager implements ILocationDao {

    private static final String UNIQUE_VIOLATION_CODE = "23505";
    public LocationDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Location save(Location location) {
        return executeInTransaction(session -> {
            try {
                session.persist(location);
            } catch (Exception e) {
                Throwable cause = e.getCause();
                if (cause instanceof PSQLException psqlException) {
                    if (UNIQUE_VIOLATION_CODE.equals(psqlException.getSQLState())) {
                        throw new ObjectAlreadyExistException(
                                String.format("The location %s already exists for %s", location.getName(),
                                              location.getUser().getLogin()), cause);
                    }
                } else {
                    throw e;
                }
            }
            return location;
        });
    }

    @Override
    public List<Location> getAll(long userId) {
        return executeInTransaction(session -> {
            String query = "SELECT l FROM Location l WHERE l.user.id = :userId";
            return session.createQuery(query, Location.class).setParameter("userId", userId).list();
        });
    }

    @Override
    public void delete(Location location) {
        sessionFactory.inTransaction(session -> session.remove(location));
    }
}

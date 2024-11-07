package com.pustovalov.weatherapplication.repository;

import com.pustovalov.weatherapplication.entity.Location;
import com.pustovalov.weatherapplication.exception.ObjectAlreadyExistException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static org.hibernate.exception.ConstraintViolationException.ConstraintKind;

@Repository
public class LocationRepository extends AbstractSessionTransactionManager implements ILocationRepository {

    public LocationRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Location save(Location location) {
        return executeInTransaction(s -> {
            try {
                s.persist(location);
            } catch (Exception e) {
                if (e instanceof ConstraintViolationException) {
                    ConstraintKind kind = ((ConstraintViolationException) e).getKind();

                    if (kind.equals(ConstraintKind.UNIQUE)) {
                        throw new ObjectAlreadyExistException(
                                "The location %s already exists for %s".formatted(location.getName(),
                                                                                  location.getUser().getLogin()));
                    }
                } else {
                    throw e;
                }
            }
            return location;
        });
    }

    @Override
    public List<Location> findAll(Long userId) {
        return executeInTransaction(s -> {
            String query = "SELECT l FROM Location l WHERE l.user.id = :userId";
            return s.createQuery(query, Location.class)
                    .setParameter("userId", userId)
                    .list();
        });
    }

    @Override
    public void delete(Long id) {
        sessionFactory.inTransaction(s -> {
            String query = "DELETE FROM Location l WHERE l.id = :id";
            s.createQuery(query)
             .setParameter("id", id)
             .executeUpdate();
        });
    }

    @Override
    public Optional<Location> findBy(Long id) {
        return executeInTransaction(s -> {
            String query = "SELECT l FROM  Location l WHERE l.id = :id";
            return s.createQuery(query, Location.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        });
    }
}

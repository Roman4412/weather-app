package com.pustovalov.weatherapplication.dao;

import com.pustovalov.weatherapplication.entity.Location;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LocationDao extends AbstractSessionTransactionManager implements ILocationDao {

    public LocationDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Location save() {
        return null;
    }

    @Override
    public Optional<Location> get() {
        return Optional.empty();
    }

    @Override
    public void delete() {

    }
}

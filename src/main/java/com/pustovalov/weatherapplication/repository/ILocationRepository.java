package com.pustovalov.weatherapplication.repository;

import com.pustovalov.weatherapplication.entity.Location;

import java.util.List;
import java.util.Optional;

public interface ILocationRepository {

    Location save(Location location);

    List<Location> findAll(Long userId);

    void delete(Long id);

    Optional<Location> findBy(Long id);
}

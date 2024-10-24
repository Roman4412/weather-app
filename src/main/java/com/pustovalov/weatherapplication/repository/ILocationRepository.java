package com.pustovalov.weatherapplication.repository;

import com.pustovalov.weatherapplication.entity.Location;

import java.util.List;

public interface ILocationRepository {

    Location save(Location location);

    List<Location> getAll(Long userId);

    void delete(Long id);
}

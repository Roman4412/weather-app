package com.pustovalov.weatherapplication.dao;

import com.pustovalov.weatherapplication.entity.Location;

import java.util.List;

public interface ILocationDao {

    Location save(Location location);

    List<Location> getAll(long userId);

    void delete(Location location);
}

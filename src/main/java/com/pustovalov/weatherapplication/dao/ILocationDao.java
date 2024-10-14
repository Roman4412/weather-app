package com.pustovalov.weatherapplication.dao;

import com.pustovalov.weatherapplication.entity.Location;

import java.util.Optional;

public interface ILocationDao {

    Location save();

    Optional<Location> get();

    void delete();
}

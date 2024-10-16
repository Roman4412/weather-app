package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.dao.ILocationDao;
import com.pustovalov.weatherapplication.dto.LocationSaveDto;
import com.pustovalov.weatherapplication.entity.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class LocationService {

    private final ILocationDao locationDao;

    private final UserService userService;

    public List<Location> getAll(long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId cannot be less than or equal to zero ");
        }
        return locationDao.getAll(userId);
    }

    public Location save(LocationSaveDto locationSaveDto) {
        if (locationSaveDto == null) {
            throw new IllegalArgumentException("location cannot be null");
        }
        Location location = new Location();
        location.setName(locationSaveDto.name());
        location.setUser(userService.findBy(locationSaveDto.userId()));
        location.setLatitude(locationSaveDto.latitude());
        location.setLongitude(locationSaveDto.longitude());
        return locationDao.save(location);
    }

    public void delete(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("location cannot be null");
        }
        locationDao.delete(location);
    }
}
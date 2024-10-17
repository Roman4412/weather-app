package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.dao.ILocationDao;
import com.pustovalov.weatherapplication.dto.LocationSaveDto;
import com.pustovalov.weatherapplication.entity.Location;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.service.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class LocationService {

    private final ILocationDao locationDao;

    private final LocationMapper locationMapper;

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
        User user = userService.findBy(locationSaveDto.userId());
        return locationDao.save(locationMapper.toEntity(locationSaveDto, user));
    }

    public void delete(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("location cannot be null");
        }
        locationDao.delete(location);
    }
}
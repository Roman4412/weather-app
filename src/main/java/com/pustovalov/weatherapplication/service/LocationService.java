package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.repository.ILocationRepository;
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

    private final ILocationRepository repository;

    private final LocationMapper mapper;

    public List<Location> getAll(Long userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId cannot be less than or equal to zero ");
        }
        return repository.findAll(userId);
    }

    public Location save(LocationSaveDto locationSaveDto) {
        if (locationSaveDto == null) {
            throw new IllegalArgumentException("location cannot be null");
        }
        User user = new User(locationSaveDto.getUserId(), "", "");
        return repository.save(mapper.toEntity(locationSaveDto, user));
    }

    public void delete(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("location's id cannot be less than or equal to zero ");
        }
        repository.delete(id);
    }
}
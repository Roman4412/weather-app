package com.pustovalov.weatherapplication.service;

import com.pustovalov.weatherapplication.dto.LocationSaveDto;
import com.pustovalov.weatherapplication.entity.Location;
import com.pustovalov.weatherapplication.entity.User;
import com.pustovalov.weatherapplication.exception.ObjectNotFoundException;
import com.pustovalov.weatherapplication.exception.UnauthorizedLocationAccessException;
import com.pustovalov.weatherapplication.repository.ILocationRepository;
import com.pustovalov.weatherapplication.service.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public void delete(Long id, Long userId) {
        if (id <= 0) {
            throw new IllegalArgumentException("location's id cannot be less than or equal to zero ");
        }
        Location location = repository.findBy(id).orElseThrow(ObjectNotFoundException::new);

        if ((!Objects.equals(location.getUser().getId(), userId))) {
            throw new UnauthorizedLocationAccessException();
        }

        repository.delete(id);
    }
}
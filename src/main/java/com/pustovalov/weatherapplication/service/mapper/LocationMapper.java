package com.pustovalov.weatherapplication.service.mapper;

import com.pustovalov.weatherapplication.dto.LocationSaveDto;
import com.pustovalov.weatherapplication.entity.Location;
import com.pustovalov.weatherapplication.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {

    @Mapping(target = "user", source = "user")
    Location toEntity(LocationSaveDto locationSaveDto, User user);
}

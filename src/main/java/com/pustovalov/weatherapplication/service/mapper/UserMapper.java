package com.pustovalov.weatherapplication.service.mapper;

import com.pustovalov.weatherapplication.dto.CreateUserFormData;
import com.pustovalov.weatherapplication.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    CreateUserFormData toDto(String login, String password);

    User toEntity(CreateUserFormData createUserFormData);
}

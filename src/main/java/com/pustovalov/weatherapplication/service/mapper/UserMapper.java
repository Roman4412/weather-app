package com.pustovalov.weatherapplication.service.mapper;

import com.pustovalov.weatherapplication.dto.CreateUserDto;
import com.pustovalov.weatherapplication.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    CreateUserDto toDto(String login, String password);

    User toEntity(CreateUserDto createUserDto);
}
